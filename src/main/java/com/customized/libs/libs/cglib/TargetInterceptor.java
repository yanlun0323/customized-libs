package com.customized.libs.libs.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author yan
 */
public class TargetInterceptor implements MethodInterceptor {


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy)
            throws Throwable {

        System.out.println("<<< Invoke Before");
        Object rt = methodProxy.invokeSuper(o, objects);
        System.out.println("Invoke After ==> " + rt + " >>> \r\n");
        return rt;
    }
}
