package com.customized.libs.core.libs.redis;

import redis.clients.jedis.Jedis;

public interface RedisCall<T> {
    T run(Jedis paramJedis);
}
