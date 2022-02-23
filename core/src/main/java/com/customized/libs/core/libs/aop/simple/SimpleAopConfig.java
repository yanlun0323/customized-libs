package com.customized.libs.core.libs.aop.simple;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/2/23 11:31
 */
@Slf4j
@Aspect
@Component
public class SimpleAopConfig {

    @Pointcut(value = "execution(public * com.customized.libs.core.libs.aop.simple..*.*(..))")
    public void log() {

    }

    @Before("log()")
    public void doBefore() {
        log.info("do before...");
    }

    @After("log()")
    public void doAfter() {
        log.info("do after...");
    }
}
