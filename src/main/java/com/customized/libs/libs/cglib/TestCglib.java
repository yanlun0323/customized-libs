package com.customized.libs.libs.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Field;

public class TestCglib {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "./libs/proxy");

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TargetObject.class);
        enhancer.setCallback(new TargetInterceptor());

        TargetObject targetObject = (TargetObject) enhancer.create();

        System.out.println(targetObject);
        System.out.println(targetObject.method0("Cglib Proxy"));
        System.out.println(targetObject.method1(1000));

        // CGLIB$CALLBACK_0为Enhancer动态代理创建出的对象，根据如下测试，可以判断出h对象为cglib生成的TargetInterceptor
        Field h = targetObject.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(Boolean.TRUE);
        System.out.println(h.getType().getName());

        Object obj = h.get(targetObject);
        System.out.println(obj.getClass());
    }
}
