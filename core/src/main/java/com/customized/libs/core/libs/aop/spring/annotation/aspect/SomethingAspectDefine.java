package com.customized.libs.core.libs.aop.spring.annotation.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/3/3 11:16
 */
@Aspect
@Component
public class SomethingAspectDefine {

    /**
     * 切点、切面
     */
    @Pointcut("execution(* com.customized.libs.core.libs.aop.spring.annotation.aspect..*.*(..))")
    public void displayTimeConsuming() {

    }

    @Around("displayTimeConsuming()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long currentTimeMillis = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        System.out.println("Time consuming ==> " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
        return proceed;
    }
}
