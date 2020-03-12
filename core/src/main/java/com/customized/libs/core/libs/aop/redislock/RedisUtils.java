package com.customized.libs.core.libs.aop.redislock;

import com.customized.libs.core.libs.aop.redislock.annos.RedisLockKey;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author yan
 */
public class RedisUtils {

    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    private static RedisUtils redisUtils;

    private static RedissonClient redissonClient;

    private RedisUtils() {
    }

    /**
     * 提供单例模式
     *
     * @return
     */
    public static RedisUtils getInstance() {
        if (redisUtils == null)
            synchronized (RedisUtils.class) {
                if (redisUtils == null) redisUtils = new RedisUtils();
            }
        return redisUtils;
    }


    /**
     * 使用config创建Redisson
     * Redisson是用于连接Redis Server的基础类
     *
     * @param config
     * @return
     */
    public static RedissonClient getRedisson(Config config) {
        RedissonClient redisson = Redisson.create(config);
        logger.info("成功连接Redis Server");
        return redisson;
    }

    /**
     * 使用ip地址和端口创建Redisson
     *
     * @param ip
     * @param port
     * @return
     */
    public static RedissonClient getRedisson(String ip, String port) {
        Config config = new Config();
        config.useSingleServer().setAddress(ip + ":" + port);
        RedissonClient redisson = Redisson.create(config);
        logger.info("成功连接Redis Server" + "\t" + "连接" + ip + ":" + port + "服务器");
        return redisson;
    }

    /**
     * 关闭Redisson客户端连接
     *
     * @param redisson
     */
    public static void closeRedisson(RedissonClient redisson) {
        redisson.shutdown();
        logger.info("成功关闭Redis Client连接");
    }

    /**
     * 获取字符串对象
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public static <T> RBucket<T> getRBucket(RedissonClient redisson, String objectName) {
        return redisson.getBucket(objectName);
    }

    /**
     * 获取Map对象
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public static <K, V> RMap<K, V> getRMap(RedissonClient redisson, String objectName) {
        return redisson.getMap(objectName);
    }

    /**
     * 获取有序集合
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public static <V> RSortedSet<V> getRSortedSet(RedissonClient redisson, String objectName) {
        return redisson.getSortedSet(objectName);
    }

    /**
     * 获取集合
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public static <V> RSet<V> getRSet(RedissonClient redisson, String objectName) {
        return redisson.getSet(objectName);
    }

    /**
     * 获取列表
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public static <V> RList<V> getRList(RedissonClient redisson, String objectName) {
        return redisson.getList(objectName);
    }

    /**
     * 获取队列
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public <V> RQueue<V> getRQueue(RedissonClient redisson, String objectName) {
        return redisson.getQueue(objectName);
    }

    /**
     * 获取双端队列
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public static <V> RDeque<V> getRDeque(RedissonClient redisson, String objectName) {
        return redisson.getDeque(objectName);
    }

    /**
     * 此方法不可用在Redisson 1.2 中
     * 在1.2.2版本中可用
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public static <V> RBlockingQueue<V> getRBlockingQueue(RedissonClient redisson, String objectName) {
        return redisson.getBlockingQueue(objectName);
    }

    /**
     * 获取锁
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public static RLock getRLock(RedissonClient redisson, String objectName) {
        return redisson.getLock(objectName);
    }

    /**
     * 获取原子数
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public static RAtomicLong getRAtomicLong(RedissonClient redisson, String objectName) {
        return redisson.getAtomicLong(objectName);
    }

    /**
     * 获取记数锁
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public static RCountDownLatch getRCountDownLatch(RedissonClient redisson, String objectName) {
        return redisson.getCountDownLatch(objectName);
    }

    /**
     * 获取消息的Topic
     *
     * @param redisson
     * @param objectName
     * @return
     */
    public static <M> RTopic<M> getRTopic(RedissonClient redisson, String objectName) {
        return redisson.getTopic(objectName);
    }

    /**
     * 获取包括方法参数上的key
     * redis key的拼写规则为 "REDIS_DIST_LOCK:" + key + ":" + @RedisLockKey<br/>
     *
     * @param point
     * @param lockKey
     * @return
     */
    public static String getLockKey(ProceedingJoinPoint point, String lockKey) {
        try {
            lockKey = "REDIS_DIST_LOCK:" + lockKey;
            Object[] args = point.getArgs();
            if (args != null && args.length > 0) {
                MethodSignature methodSignature = (MethodSignature) point.getSignature();
                Annotation[][] parameterAnnotations = methodSignature.getMethod().getParameterAnnotations();
                if (parameterAnnotations != null && parameterAnnotations.length > 0) {
                    SortedMap<Integer, String> keys = new TreeMap<>();
                    for (int i = 0; i < parameterAnnotations.length; i++) {
                        RedisLockKey redisLockKey = getAnnotation(RedisLockKey.class, parameterAnnotations[i]);
                        if (redisLockKey != null) {
                            Object arg = args[i];
                            if (arg != null) {
                                keys.put(redisLockKey.order(), arg.toString());
                            }
                        }
                    }
                    if (keys.size() > 0) {
                        for (String key : keys.values()) {
                            lockKey = lockKey + ":" + key;
                        }
                    }
                }
            }
            return lockKey;
        } catch (Exception e) {
            logger.error("getLockKey error.", e);
        }
        return null;
    }

    /**
     * 获取注解类型
     *
     * @param annotationClass
     * @param annotations
     * @param <T>
     * @return
     */
    private static <T extends Annotation> T getAnnotation(final Class<T> annotationClass, final Annotation[] annotations) {
        if (annotations != null && annotations.length > 0) {
            for (final Annotation annotation : annotations) {
                if (annotationClass.equals(annotation.annotationType())) {
                    return (T) annotation;
                }
            }
        }
        return null;
    }

    public static RedissonClient createClient(String address, String pass) {
        if (redissonClient == null) {
            synchronized (RedisUtils.class) {
                if (redissonClient == null) {
                    Config config = new Config();
                    SingleServerConfig singleSerververConfig = config.useSingleServer();
                    singleSerververConfig.setAddress(address);
                    singleSerververConfig.setPassword(pass);
                    redissonClient = getRedisson(config);
                }
            }
        }
        return redissonClient;
    }
}