package com.customized.libs.core.libs.mybatis.plugin.dynamic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyDynamicProxyV1 {

    public interface HelloService {
        void sayHello();
    }

    public static class HelloServiceImpl implements HelloService {

        @Override
        public void sayHello() {
            System.out.println("Say Hello!!");
        }
    }

    public static class Plugin0 implements InvocationHandler {

        private Object target;

        public Plugin0(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("前置通知处理");
            Object invoke = method.invoke(target, args);
            System.out.println("后置通知处理");
            return invoke;
        }

        public static Object wrap(Object target) {
            return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                    target.getClass().getInterfaces(),
                    new Plugin0(target)
            );
        }
    }

    public static class Plugin1 implements InvocationHandler {

        private Object target;

        public Plugin1(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Global前置通知处理");
            Object invoke = method.invoke(target, args);
            System.out.println("Global后置通知处理");
            return invoke;
        }

        public static Object wrap(Object target) {
            return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                    target.getClass().getInterfaces(),
                    new Plugin1(target)
            );
        }
    }

    public static void main(String[] args) {
        HelloService proxyService = (HelloService) Plugin0.wrap(new HelloServiceImpl());
        proxyService = (HelloService) Plugin1.wrap(proxyService);
        proxyService.sayHello();
    }
}
