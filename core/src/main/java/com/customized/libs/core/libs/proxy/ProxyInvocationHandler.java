package com.customized.libs.core.libs.proxy;

import com.customized.libs.core.libs.proxy.interfaces.ProxyIndex;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyInvocationHandler implements InvocationHandler {

    public ProxyIndex target;

    public ProxyInvocationHandler(ProxyIndex target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("JDK Proxy Impl");
        return method.invoke(target, args);
    }
}
