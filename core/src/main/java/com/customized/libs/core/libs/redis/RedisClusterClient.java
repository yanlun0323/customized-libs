package com.customized.libs.core.libs.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class RedisClusterClient {
    private static final Log logger = LogFactory.getLog(RedisClusterClient.class);
    private static JedisCluster pool;
    private static boolean newVersion = false;

    static {
        init();
    }

    public static JedisCluster getResource() {
        if (pool == null) {
            throw new RuntimeException("Redis Client not init!");
        }
        return pool;
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
            String[] hostAndPorts = resourceBundle.getString("cluster").trim().split(",");
            if (hostAndPorts.length > 1) {
                Set<HostAndPort> set = new HashSet();
                for (String name : hostAndPorts) {
                    String[] hp = name.split(":");
                    HostAndPort e = new HostAndPort(hp[0], Integer.parseInt(hp[1]));
                    set.add(e);
                }
                pool = new JedisCluster(set, toInteger("timeout"), toInteger("timeout"), 5, resourceBundle.getString("password"), config);
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

    public static void main(String[] args) {
    }
}
