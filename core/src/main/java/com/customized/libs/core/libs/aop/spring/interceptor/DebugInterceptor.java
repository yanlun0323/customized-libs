package com.customized.libs.core.libs.aop.spring.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class DebugInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("Before: invocation=[" + invocation + "]");
        // 执行 真实实现类 的方法
        Object rval = invocation.proceed();
        System.out.println("Invocation returned");
        return rval;
    }
}