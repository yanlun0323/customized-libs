package com.customized.libs.core.libs.aop.redislock.annos;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RedisLock {
    /**
     * 锁的key
     * 如果想添加非固定锁，可以在参数上添加@P4jSynKey注解，但是本参数是必写选项<br/>
     * redis key的拼写规则为 "REDIS_DIST_LOCK:" + key + ":" + @RedisLockKey<br/>
     */
    String key();

    /**
     * 持锁时间
     * 单位毫秒,默认10秒<br/>
     */
    long keepMills() default 10 * 1000;

    /**
     * 没有获取到锁时，等待时间
     * 单位毫秒,默认120秒<br/>
     *
     * @return
     */
    long maxSleepMills() default 120 * 1000;
}