package com.customized.libs.core.libs.redis;

import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Map;
import java.util.Set;


@SuppressWarnings("unchecked")
public class RedisFactory {

    public static boolean setStringExpireTime(String key, final int expireTime) {
        return RedisClientUtils.call(jedis -> {
            try {
                jedis.expire(key, expireTime);
                return Boolean.TRUE;
            } catch (Exception e) {
            }
            return Boolean.FALSE;
        });
    }

    public static boolean setObjectCache(String key, Object obj, int expireTime) {
        return setStringCache(key, obj.toString(), expireTime);
    }

    /*public static boolean setJSONObjectCache(String key, Object obj, int expireTime) {
        String val = new JSONObject(obj).toString();
        return setStringCache(key, val, expireTime);
    }

    public static Object getJSONObjectCache(String key, Class cls) {
        String val = (String) RedisClientUtils.call(new RedisCall() {
            public String run(Jedis jedis) {
                return jedis.get(this.val$key);
            }
        });
        if (StringUtils.isBlank(val)) {
            return null;
        }
        return JSONUtils.jsonToBean(val, cls);
    }*/

    public static Object getObjectCache(String key, Class cls) {
        String val = RedisClientUtils.call(jedis -> jedis.get(key));
        if (StringUtils.isBlank(val)) {
            return null;
        }
        return val;
    }

    public static boolean setNxStringCache(String key, String val, int expireTime) {
        return setNx(key, val, expireTime);
    }

    public static boolean setStringCache(String key, final String val, final int expireTime) {
        return (Boolean) RedisClientUtils.call((RedisCall) jedis -> {
            try {
                jedis.set(key, val);
                jedis.expire(key, expireTime);
                return Boolean.TRUE;
            } catch (Exception e) {
            }
            return Boolean.FALSE;
        });
    }

    public static boolean setStringCache(List<String> keys, final String value, final int expireTime, final String payType) {
        return (Boolean) RedisClientUtils.call((RedisCall) jedis -> {
            try {
                Pipeline pipelined = jedis.pipelined();
                for (String key : keys) {
                    pipelined.set(key + payType, value);
                    pipelined.expire(key + payType, expireTime);
                }
                pipelined.sync();
                return Boolean.TRUE;
            } catch (Exception e) {
            }
            return Boolean.FALSE;
        });
    }

    public static boolean setStringCache(Map<String, String> map, final int expireTime, final String type) {
        return (Boolean) RedisClientUtils.call(jedis -> {
            try {
                Pipeline pipelined = jedis.pipelined();
                Set<String> keys = map.keySet();
                for (String key : keys) {
                    pipelined.set(key + type, (String) map.get(key));
                    pipelined.expire(key + type, expireTime);
                }
                pipelined.sync();
                return Boolean.TRUE;
            } catch (Exception e) {
            }
            return Boolean.FALSE;
        });
    }

    public static String getStringCache(String key) {
        return (String) RedisClientUtils.call((RedisCall) jedis -> jedis.get(key));
    }

    private static boolean setNx(String key, final String val, final int expireTime) {
        return (Boolean) RedisClientUtils.call((RedisCall) jedis -> {
            if (RedisClientUtils.isNewVersion()) {
                String response = jedis.set(key, val, "NX", "PX", expireTime);
                return "OK".equals(response);
            }
            if (jedis.setnx(key, val) == 1L) {
                jedis.expire(key, expireTime);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        });
    }

    public static boolean setNx(String key, final String val) {
        return (Boolean) RedisClientUtils.call((RedisCall) jedis -> {
            if (RedisClientUtils.isNewVersion()) {
                String response = jedis.set(key, val, "NX");
                return "OK".equals(response);
            }
            if (jedis.setnx(key, val) == 1L) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        });
    }

    public static boolean setParentMapCache(String key, final Map map, final int expireTime) {
        return (Boolean) RedisClientUtils.call((RedisCall) jedis -> {
            String response = jedis.hmset(key, map);
            jedis.expire(key, expireTime);
            return "OK".equals(response);
        });
    }

    public static boolean setOneKeyFromMapCache(String parentKey, final String key, final Object obj, final int expireTime) {
        return RedisClientUtils.call(jedis -> {
            Long l = jedis.hsetnx(parentKey, key, obj.toString());
            jedis.expire(key, expireTime);
            return l > 0L;
        });
    }

    public Map getParentMapCache(final String key) {
        return RedisClientUtils.call(jedis -> jedis.hgetAll(key));
    }

    public static Object getOneKeyFromMapCache(String parentKey, final String key) {
        return RedisClientUtils.call(jedis -> jedis.hmget(parentKey, new String[]{key}));
    }

    public static Object getValueFromMap(String key, final String field) {
        return RedisClientUtils.call((RedisCall) jedis -> jedis.hget(key, field));
    }

    public boolean hasParentMapKey(final String parentKey) {
        return RedisClientUtils.call(jedis -> jedis.exists(parentKey));
    }

    public static boolean removeKey(String key) {
        return (Boolean) RedisClientUtils.call((RedisCall) jedis -> {
            long l = jedis.del(key);
            return l > 0L;
        });
    }

    public static boolean setOneKeyFromMap(String parentKey, final String key, final String obj) {
        return (Boolean) RedisClientUtils.call((RedisCall) jedis -> {
            Long l = jedis.hset(parentKey, key, obj);
            return l > 0L;
        });
    }

    public static Long deleteOneKeyFromMap(String parentKey, final String key) {
        return (Long) RedisClientUtils.call((RedisCall) jedis -> jedis.hdel(parentKey, new String[]{key}));
    }

    public static String getOneKeyFromMap(String parentKey, final String key) {
        return (String) RedisClientUtils.call((RedisCall) jedis -> jedis.hget(parentKey, key));
    }

    public static boolean setStringCache(String key, final String val) {
        return (Boolean) RedisClientUtils.call((RedisCall) jedis -> {
            try {
                jedis.set(key, val);
                return Boolean.TRUE;
            } catch (Exception e) {
            }
            return Boolean.FALSE;
        });
    }

    public static Long increment(String key) {
        return (Long) RedisClientUtils.call((RedisCall) jedis -> jedis.incr(key));
    }

    public static Long incrementBy(String key, final long value, int expireTime) {
        return (Long) RedisClientUtils.call((RedisCall) jedis -> {
            long val = jedis.incrBy(key, value);
            jedis.expire(key, expireTime);
            return val;
        });
    }

    public static Long incrementBy(String key, final long expireTime) {
        return (Long) RedisClientUtils.call((RedisCall) jedis -> (long) jedis.incrBy(key, expireTime));
    }

    public static Object lrange(String key, final long expireTime) {
        return RedisClientUtils.call((RedisCall) jedis -> jedis.lrange(key, expireTime, expireTime));
    }

    public static Long hashIncrement(String key, final String field, final long expireTime) {
        return (Long) RedisClientUtils.call((RedisCall) jedis -> jedis.hincrBy(key, field, expireTime));
    }

    public static long zaddWithExpire(String key, double score, String member, int seconds) {
        long s = zadd(key, score, member);
        setKeyExpireTime(key, seconds);
        return s;
    }

    public static boolean zaddWithExpirePiLiang(final String redisKey, Map<String, Double> map, final int seconds) {
        return (Boolean) RedisClientUtils.call(jedis -> {
            try {
                Pipeline pipelined = jedis.pipelined();
                Set<String> keys = map.keySet();
                for (String key : keys) {
                    pipelined.zadd(redisKey, map.get(key), key);
                    pipelined.expire(key, seconds);
                }
                pipelined.sync();
                return Boolean.TRUE;
            } catch (Exception e) {
            }
            return Boolean.FALSE;
        });
    }

    public static long zadd(String key, final double score, String member) {
        return (Long) RedisClientUtils.call((RedisCall) jedis -> jedis.zadd(key, score, member));
    }

    public static long setKeyExpireTime(String key, final int seconds) {
        return (Long) RedisClientUtils.call((RedisCall) jedis -> jedis.expire(key, seconds));
    }

    public static long zcount(String key, final double min, double max) {
        return (Long) RedisClientUtils.call((RedisCall) jedis -> jedis.zcount(key, min, max));
    }

    public static long lpush(String key, final String value) {
        return (Long) RedisClientUtils.call((RedisCall) jedis -> jedis.lpush(key, new String[]{value}));
    }

    public static long llen(String key) {
        return (Long) RedisClientUtils.call((RedisCall) jedis -> jedis.llen(key));
    }

    public static Object lpop(String key) {
        return RedisClientUtils.call((RedisCall) jedis -> jedis.lpop(key));
    }

    public static long rpush(String key, final String value) {
        return (Long) RedisClientUtils.call((RedisCall) jedis -> jedis.rpush(key, new String[]{value}));
    }

    public static Object rpop(String key) {
        return RedisClientUtils.call((RedisCall) jedis -> jedis.rpop(key));
    }

    public static String rpoplpush(String key1, final String key2) {
        return (String) RedisClientUtils.call((RedisCall) jedis -> jedis.rpoplpush(key1, key2));
    }

    public static String brpoplpushtimeout(String key1, final String key2, final int timeout) {
        return (String) RedisClientUtils.call((RedisCall) jedis -> jedis.brpoplpush(key1, key2, timeout));
    }

    public static String brpoplpush(String key1, final String key2) {
        return (String) RedisClientUtils.call((RedisCall) jedis -> jedis.rpoplpush(key1, key2));
    }

    public static Long lrem(String key1, final int count, final String value) {
        return (Long) RedisClientUtils.call((RedisCall) jedis -> jedis.lrem(key1, count, value));
    }
}
