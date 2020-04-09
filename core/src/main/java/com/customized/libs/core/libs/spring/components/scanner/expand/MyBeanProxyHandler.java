package com.customized.libs.core.libs.spring.components.scanner.expand;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author yan
 */
@Slf4j
public class MyBeanProxyHandler<T> implements InvocationHandler {

    private T targetImpl;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long currentTimeMillis = System.currentTimeMillis();
        Object rt = method.invoke(targetImpl, args);
        log.warn("time consuming ==> {}ms", System.currentTimeMillis() - currentTimeMillis);
        return rt;
    }

    public void setTargetImpl(T targetImpl) {
        this.targetImpl = targetImpl;
    }
}
