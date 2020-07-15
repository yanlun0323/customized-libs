package com.customized.libs.core.libs.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisMovedDataException;
import redis.clients.jedis.exceptions.JedisRedirectionException;
import redis.clients.util.JedisClusterCRC16;
import redis.clients.util.SafeEncoder;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author yan
 */
public class JedisClusterPipeline extends PipelineBase implements Closeable {

    private static final Logger LOGGER = LoggerFactory.getLogger(JedisClusterPipeline.class);
    private static final Field FIELD_CONNECTION_HANDLER = getField(BinaryJedisCluster.class, "connectionHandler");
    private static final Field FIELD_CACHE = getField(JedisClusterConnectionHandler.class, "cache");
    private JedisSlotBasedConnectionHandler connectionHandler;
    private JedisClusterInfoCache clusterInfoCache;
    private Queue<Client> clients = new LinkedList();
    private Map<JedisPool, Jedis> jedisMap = new HashMap();
    private boolean hasDataInBuf = false;

    public static JedisClusterPipeline pipelined(JedisCluster jedisCluster) {
        JedisClusterPipeline pipeline = new JedisClusterPipeline();
        pipeline.setJedisCluster(jedisCluster);
        return pipeline;
    }

    public void setJedisCluster(JedisCluster jedis) {
        this.connectionHandler = ((JedisSlotBasedConnectionHandler) getValue(jedis, FIELD_CONNECTION_HANDLER));
        this.clusterInfoCache = ((JedisClusterInfoCache) getValue(this.connectionHandler, FIELD_CACHE));
    }

    public void refreshCluster() {
        this.connectionHandler.renewSlotCache();
    }

    public void sync() {
        innerSync(null);
    }

    public List<Object> syncAndReturnAll() {
        List<Object> responseList = new ArrayList();

        innerSync(responseList);

        return responseList;
    }

    private void innerSync(List<Object> formatted) {
        HashSet<Client> clientSet = new HashSet();
        try {
            for (Client client : this.clients) {
                Object data = generateResponse(client.getOne()).get();
                if (null != formatted) {
                    formatted.add(data);
                }
                if (clientSet.size() != this.jedisMap.size()) {
                    clientSet.add(client);
                }
            }
        } catch (JedisRedirectionException jre) {
            Jedis jedis;
            if ((jre instanceof JedisMovedDataException)) {
                refreshCluster();
            }
            throw jre;
        } finally {
            if (clientSet.size() != this.jedisMap.size()) {
                for (Jedis jedis : this.jedisMap.values()) {
                    if (!clientSet.contains(jedis.getClient())) {
                        flushCachedData(jedis);
                    }
                }
            }
            this.hasDataInBuf = false;
            close();
        }
    }

    @Override
    public void close() {
        clean();

        this.clients.clear();
        for (Jedis jedis : this.jedisMap.values()) {
            if (this.hasDataInBuf) {
                flushCachedData(jedis);
            }
            jedis.close();
        }
        this.jedisMap.clear();

        this.hasDataInBuf = false;
    }

    private void flushCachedData(Jedis jedis) {
        try {
            jedis.getClient().getAll();
        } catch (RuntimeException localRuntimeException) {
        }
    }

    @Override
    protected Client getClient(String key) {
        byte[] bKey = SafeEncoder.encode(key);

        return getClient(bKey);
    }

    @Override
    protected Client getClient(byte[] key) {
        Jedis jedis = getJedis(JedisClusterCRC16.getSlot(key));

        Client client = jedis.getClient();
        this.clients.add(client);

        return client;
    }

    private Jedis getJedis(int slot) {
        JedisPool pool = this.clusterInfoCache.getSlotPool(slot);

        Jedis jedis = (Jedis) this.jedisMap.get(pool);
        if (null == jedis) {
            jedis = pool.getResource();
            this.jedisMap.put(pool, jedis);
        }
        this.hasDataInBuf = true;
        return jedis;
    }

    private static Field getField(Class<?> cls, String fieldName) {
        try {
            Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);

            return field;
        } catch (NoSuchFieldException | SecurityException e) {
            throw new RuntimeException("cannot find or access field '" + fieldName + "' from " + cls.getName(), e);
        }
    }

    private static <T> T getValue(Object obj, Field field) {
        try {
            return (T) field.get(obj);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            LOGGER.error("get value fail", e);

            throw new RuntimeException(e);
        }
    }

    public static boolean setStringCacer(List<String> keys, String value, int expireTime, String payType) {
        JedisCluster jc = null;
        JedisClusterPipeline jcp = null;
        try {
            jc = RedisClusterClient.getResource();
            jcp = pipelined(jc);
            jcp.refreshCluster();
            for (String key : keys) {
                jcp.setex(key + payType, expireTime, value);
            }
            jcp.sync();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jcp != null) {
                jcp.close();
            }
        }
        return false;
    }

    public static boolean setStringCache(String key, String val, int expireTime) {
        try {
            JedisCluster jc = RedisClusterClient.getResource();
            jc.setex(key, expireTime, val);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getStringCache(String key) {
        JedisCluster jc = RedisClusterClient.getResource();
        return jc.get(key);
    }

    public static void main(String[] args)
            throws IOException {
        JedisCluster jc = RedisClusterClient.getResource();

        long s = System.currentTimeMillis();

        JedisClusterPipeline jcp = pipelined(jc);
        jcp.refreshCluster();
        List<Object> batchResult = null;
        try {
            for (int i = 0; i < 100; i++) {
                jcp.setex("k" + i, 100, "v1" + i);
            }
            jcp.sync();
            for (int i = 0; i < 100; i++) {
                jcp.get("k" + i);
            }
            batchResult = jcp.syncAndReturnAll();
        } finally {
            jcp.close();
        }
        long t = System.currentTimeMillis() - s;
        System.out.println(t);

        System.out.println(batchResult.size());
        System.out.println(batchResult.toString());

        System.out.println(RedisFactory.getStringCache("k1"));

        jc.close();
    }
}
