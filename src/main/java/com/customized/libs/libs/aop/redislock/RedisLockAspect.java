package com.customized.libs.libs.aop.redislock;

import com.customized.libs.libs.aop.redislock.annos.RedisLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RedisLockAspect {

    private static Logger logger = LoggerFactory.getLogger(RedisLockAspect.class);

    @Value("redis://${spring.redis.host}:${spring.redis.port}")
    String address;

    @Around("execution(* com.customized.libs.service..*(..)) && @annotation(com.customized.libs.libs.aop.redislock.annos.RedisLock)")
    public Object lock(ProceedingJoinPoint point) throws Throwable {
        RLock lock = null;
        Object object = null;
        try {
            RedisLock redisLock = getDistRedisLock(point);
            if (Objects.nonNull(redisLock)) {
                RedissonClient redissonClient;
                redissonClient = RedisUtils.createClient(address, null);
                String lockKey = RedisUtils.getLockKey(point, redisLock.key());

                lock = RedisUtils.getRLock(redissonClient, lockKey);
                if (lock != null) {
                    // Wait for maxSleepMills and automatically unlock it after lockTime seconds
                    long startTimeMillis = System.currentTimeMillis();

                    Boolean locked = lock.tryLock(redisLock.maxSleepMills(), redisLock.keepMills(), TimeUnit.MILLISECONDS);
                    logger.warn("key=>{}, get lock=>{}, delay=>{}", redisLock.key(),
                            locked, System.currentTimeMillis() - startTimeMillis);
                    if (locked) {
                        object = point.proceed();
                    }
                    logger.warn("total time consuming=>{}", System.currentTimeMillis() - startTimeMillis);
                }
            }
        } finally {
            if (lock != null) {
                lock.forceUnlock();
            }
        }
        return object;
    }

    private RedisLock getDistRedisLock(ProceedingJoinPoint point) {
        try {
            MethodSignature methodSignature = (MethodSignature) point.getSignature();
            Method method = methodSignature.getMethod();
            return method.getAnnotation(RedisLock.class);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }
}