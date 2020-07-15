package com.customized.libs.core.libs.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RedisLock {
    public static final int DEFAULT_EXPIRE_TIME = 10000;
    public static final String SET_SUCCESS = "OK";
    private int expire;
    private String key;
    private String val;
    private static final Log LOG = LogFactory.getLog(RedisClientUtils.class);

    public RedisLock(String key) {
        this.key = key;
        this.val = UUID.randomUUID().toString();
        this.expire = 10000;
    }

    public RedisLock(String key, int expire) {
        this.key = key;
        this.val = UUID.randomUUID().toString();
        if (expire > 0) {
            this.expire = (expire * 1000);
        } else {
            this.expire = 10000;
        }
    }

    private boolean setNx(final String key, final String val, final int expireTime) {
        return (Boolean) RedisClientUtils.call((RedisCall) jedis -> {
            if (RedisClientUtils.isNewVersion()) {
                String response = jedis.set(key, val, "NX", "PX", expireTime);

                return Boolean.valueOf("OK".equals(response));
            }
            if (jedis.setnx(key, val).longValue() == 1L) {
                jedis.expire(key, expireTime / 1000);
                return Boolean.valueOf(true);
            }
            return Boolean.valueOf(false);
        });
    }

    public boolean lock() {
        return (Boolean) RedisClientUtils.call((RedisCall) jedis -> {
            if (RedisLock.this.setNx(RedisLock.this.key, RedisLock.this.val, RedisLock.this.expire)) {
                if (RedisLock.LOG.isDebugEnabled()) {
                    RedisLock.LOG.debug("get lock success ,key=" + RedisLock.this.key + ", expire seconds=" +
                            RedisLock.this.expire);
                }
                return Boolean.valueOf(true);
            }
            RedisLock.LOG.debug("get lock fail, key=" + RedisLock.this.key);
            return Boolean.valueOf(false);
        });
    }

    public boolean tryLock(int timeout) {
        long tryTime = System.currentTimeMillis() + timeout * 1000L;
        while (System.currentTimeMillis() < tryTime) {
            if (setNx(this.key, this.val, this.expire)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Get redis lock success ,key=" + this.key + ", expire, seconds=" + this.expire);
                }
                return true;
            }
            LOG.debug("get lock fail, key=" + this.key);
            try {
                TimeUnit.MILLISECONDS.sleep(50L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return false;
    }

    public void unlock() {
        RedisClientUtils.call((RedisCall) jedis -> {
            jedis.watch(new String[]{RedisLock.this.key});
            String ser_val = jedis.get(RedisLock.this.key);
            if (RedisLock.this.val.equals(ser_val)) {
                jedis.del(RedisLock.this.key);
            }
            jedis.unwatch();
            return Boolean.valueOf(false);
        });
    }
}
