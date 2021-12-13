package com.customized.libs.core.libs.mybatis.plugin.dynamic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyDynamicProxyV2 {

    public interface HelloService {
        void sayHello();
    }

    public static class HelloServiceImpl implements HelloService {

        @Override
        public void sayHello() {
            System.out.println("Say Hello!!");
        }
    }

    public interface Interceptor {
        /**
         * 具体拦截处理
         */
        void intercept();
    }

    public static class Plugin implements InvocationHandler {

        private Object target;

        private Interceptor interceptor;

        public Plugin(Object target, Interceptor interceptor) {
            this.target = target;
            this.interceptor = interceptor;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            interceptor.intercept();
            return method.invoke(target, args);
        }

        public static Object wrap(Object target, Interceptor interceptor) {
            return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                    target.getClass().getInterfaces(),
                    new Plugin(target, interceptor)
            );
        }
    }
    
    public static class LogInterceptor implements Interceptor {

        @Override
        public void intercept() {
            System.out.println("Logger 前置处理");
        }
    }

    public static void main(String[] args) {
        HelloService proxyService = (HelloService) Plugin.wrap(new HelloServiceImpl(), new LogInterceptor());
        proxyService.sayHello();
    }
}
