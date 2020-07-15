package com.customized.libs.core.libs.redis.test;

import com.customized.libs.core.libs.redis.RedisClientUtils;
import com.customized.libs.core.libs.redis.RedisClusterClient;

public class RedisConnetTest {

    public static void main(String[] args) {

        System.out.println(RedisClusterClient.getResource().get("kayou:system:dev"));

        System.out.println(RedisClientUtils.getResource().get("20scan:unipay:order:id"));
    }
}
