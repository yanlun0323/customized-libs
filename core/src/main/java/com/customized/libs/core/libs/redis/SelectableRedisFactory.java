package com.customized.libs.core.libs.redis;

import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class SelectableRedisFactory {


    public static boolean setStringExpireTime(String key, final int expireTime, SelectableRedisClientUtils util) {
        return (Boolean) util.call((RedisCall) jedis -> {
            try {
                jedis.expire(key, expireTime);
                return Boolean.TRUE;
            } catch (Exception e) {
            }
            return Boolean.FALSE;
        });
    }

    public static boolean setObjectCache(String key, Object obj, int expireTime, SelectableRedisClientUtils util) {
        return setStringCache(key, obj.toString(), expireTime, util);
    }

    /*public static boolean setJSONObjectCache(String key, Object obj, int expireTime, SelectableRedisClientUtils util) {
        String val = new JSONObject(obj).toString();
        return setStringCache(key, val, expireTime, util);
    }

    public static Object getJSONObjectCache(String key, Class cls, SelectableRedisClientUtils util) {
        String val = (String) util.call(new RedisCall() {
            public String run(Jedis jedis) {
                return jedis.get(key);
            }
        });
        if (StringUtils.isBlank(val)) {
            return null;
        }
        return JSONUtils.jsonToBean(val, cls);
    }*/

    public static Object getObjectCache(String key, Class cls, SelectableRedisClientUtils util) {
        String val = (String) util.call((RedisCall) jedis -> jedis.get(key));
        if (StringUtils.isBlank(val)) {
            return null;
        }
        return val;
    }

    public static boolean setNxStringCache(String key, String val, int expireTime, SelectableRedisClientUtils util) {
        return setNx(key, val, expireTime, util);
    }

    public static boolean setStringCache(String key, final String val, final int expireTime, SelectableRedisClientUtils util) {
        return (Boolean) util.call((RedisCall) jedis -> {
            try {
                jedis.set(key, val);
                jedis.expire(key, expireTime);
                return Boolean.TRUE;
            } catch (Exception e) {
            }
            return Boolean.FALSE;
        });
    }

    public static boolean setStringCache(List<String> keys, final String value, final int expireTime, final String payType, SelectableRedisClientUtils util) {
        return (Boolean) util.call((RedisCall) jedis -> {
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

    public static boolean setStringCache(Map<String, String> map, final int expireTime, final String type, SelectableRedisClientUtils util) {
        return util.call(jedis -> {
            try {
                Pipeline pipelined = jedis.pipelined();
                Set<String> keys = map.keySet();
                for (String key : keys) {
                    pipelined.set(key + type, map.get(key));
                    pipelined.expire(key + type, expireTime);
                }
                pipelined.sync();
                return Boolean.TRUE;
            } catch (Exception e) {
            }
            return Boolean.FALSE;
        });
    }

    public static String getStringCache(String key, SelectableRedisClientUtils util) {
        return (String) util.call((RedisCall) jedis -> jedis.get(key));
    }

    private static boolean setNx(String key, final String val, final int expireTime, SelectableRedisClientUtils util) {
        return (Boolean) util.call((RedisCall) jedis -> {
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

    public static boolean setParentMapCache(String key, final Map map, final int expireTime, SelectableRedisClientUtils util) {
        return (Boolean) util.call((RedisCall) jedis -> {
            String response = jedis.hmset(key, map);
            jedis.expire(key, expireTime);
            return "OK".equals(response);
        });
    }

    public static boolean setOneKeyFromMapCache(String parentKey, final String key, final Object obj, final int expireTime, SelectableRedisClientUtils util) {
        return (Boolean) util.call((RedisCall) jedis -> {
            Long l = jedis.hsetnx(parentKey, key, obj.toString());
            jedis.expire(key, expireTime);
            return l > 0L;
        });
    }

    public Map getParentMapCache(final String key, SelectableRedisClientUtils util) {
        return (Map) util.call((RedisCall) jedis -> (Map) jedis.hgetAll(key));
    }

    public static Object getOneKeyFromMapCache(String parentKey, final String key, SelectableRedisClientUtils util) {
        return util.call((RedisCall) jedis -> jedis.hmget(parentKey, new String[]{key}));
    }

    public static Object getValueFromMap(String key, final String field, SelectableRedisClientUtils util) {
        return util.call((RedisCall) jedis -> jedis.hget(key, field));
    }

    public boolean hasParentMapKey(final String parentKey, SelectableRedisClientUtils util) {
        return (Boolean) util.call((RedisCall) jedis -> jedis.exists(parentKey));
    }

    public static boolean removeKey(String key, SelectableRedisClientUtils util) {
        return (Boolean) RedisClientUtils.call((RedisCall) jedis -> {
            long l = jedis.del(key);
            return l > 0L;
        });
    }

    public static boolean setOneKeyFromMap(String parentKey, final String key, final String obj, SelectableRedisClientUtils util) {
        return (Boolean) util.call((RedisCall) jedis -> {
            Long l = jedis.hset(parentKey, key, obj);
            return l > 0L;
        });
    }

    public static Long deleteOneKeyFromMap(String parentKey, final String key, SelectableRedisClientUtils util) {
        return (Long) util.call((RedisCall) jedis -> jedis.hdel(parentKey, new String[]{key}));
    }

    public static String getOneKeyFromMap(String parentKey, final String key, SelectableRedisClientUtils util) {
        return (String) util.call((RedisCall) jedis -> jedis.hget(parentKey, key));
    }

    public static boolean setStringCache(String key, final String val, SelectableRedisClientUtils util) {
        return (Boolean) util.call((RedisCall) jedis -> {
            try {
                jedis.set(key, val);
                return Boolean.TRUE;
            } catch (Exception e) {
            }
            return Boolean.FALSE;
        });
    }

    public static Long increment(String key, SelectableRedisClientUtils util) {
        return (Long) util.call((RedisCall) jedis -> jedis.incr(key));
    }

    public static Long incrementBy(String key, final long value, int time, SelectableRedisClientUtils util) {
        return (Long) util.call((RedisCall) jedis -> {
            long val = jedis.incrBy(key, value);
            jedis.expire(key, time);
            return val;
        });
    }

    public static Long incrementBy(String key, final long value, SelectableRedisClientUtils util) {
        return (Long) util.call((RedisCall) jedis -> {
            return (long) jedis.incrBy(key, value);
        });
    }

    public static List<String> lrange(String key, final long index, SelectableRedisClientUtils util) {
        return (List) util.call((RedisCall) jedis -> jedis.lrange(key, index, index));
    }

    public static Long hashIncrement(String key, final String field, final long value, SelectableRedisClientUtils util) {
        return (Long) util.call((RedisCall) jedis -> jedis.hincrBy(key, field, value));
    }
}
