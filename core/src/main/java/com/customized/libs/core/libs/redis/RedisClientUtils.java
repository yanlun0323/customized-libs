package com.customized.libs.core.libs.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.util.Pool;

import java.util.*;

public class RedisClientUtils {
    private static final Log logger = LogFactory.getLog(RedisClientUtils.class);
    private static Pool<Jedis> pool;
    private static boolean newVersion = false;

    static {
        init();
    }

    public static Jedis getResource() {
        if (pool == null) {
            throw new RuntimeException("Redis Client not init!");
        }
        return (Jedis) pool.getResource();
    }

    public static void closeResource(Jedis jedis) {
        pool.returnResource(jedis);
    }

    public static void closeBrokenResource(Jedis jedis) {
        pool.returnBrokenResource(jedis);
    }

    private static ResourceBundle resourceBundle = null;

    public static synchronized void init() {
        try {
            Locale locale1 = new Locale("zh", "CN");
            resourceBundle = ResourceBundle.getBundle("config/redis-config", locale1);
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(toInteger("maxTotal"));
            config.setMaxIdle(toInteger("maxIdle"));
            config.setMaxWaitMillis(toInteger("maxWaitMillis"));
            config.setTestOnBorrow(toBoolean("testOnBorrow"));
            config.setTestOnReturn(toBoolean("testOnReturn"));
            String[] hostAndPorts = resourceBundle.getString("sentinels").trim().split(",");
            if (hostAndPorts.length > 1) {
                Set<String> sentinels = new HashSet(Arrays.asList(hostAndPorts));

                pool = new JedisSentinelPool(resourceBundle.getString("masterName").trim(), sentinels, config, resourceBundle.getString("password").trim());
            } else {
                String[] hostAndPort = hostAndPorts[0].split(":");
                String host = hostAndPort[0];
                int port = Integer.parseInt(hostAndPort[1]);

                pool = new JedisPool(config, host, port, toInteger("timeout"), resourceBundle.getString("password"), 0, null);
            }
        } catch (Exception e) {
            logger.error("init redis pool fail", e);
            throw new RuntimeException("init redis pool fail", e);
        }
    }

    private static int toInteger(String key) {
        return Integer.parseInt(resourceBundle.getString(key).trim());
    }

    private static boolean toBoolean(String key) {
        return Boolean.valueOf(resourceBundle.getString(key).trim()).booleanValue();
    }

    public static boolean isNewVersion() {
        return newVersion;
    }

    public static <T> T call(RedisCall<T> rc) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return (T) rc.run(jedis);
        } finally {
            if (jedis != null) {
                closeResource(jedis);
            }
        }
    }

    public static void main(String[] args) {
    }
}
