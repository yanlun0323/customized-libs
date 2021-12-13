package com.customized.libs.core.libs.mybatis.plugin.dynamic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyDynamicProxyV3 {

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
        Object intercept(Invocation invocation) throws Exception;
    }

    public static class Invocation {

        /**
         * 目标对象
         */
        private Object target;
        /**
         * 执行的方法
         */
        private Method method;
        /**
         * 方法的参数
         */
        private Object[] args;

        public Object getTarget() {
            return target;
        }

        public void setTarget(Object target) {
            this.target = target;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public Object[] getArgs() {
            return args;
        }

        public void setArgs(Object[] args) {
            this.args = args;
        }

        public Invocation(Object target, Method method, Object[] args) {
            this.target = target;
            this.method = method;
            this.args = args;
        }

        /**
         * 执行目标对象的方法
         */
        public Object process() throws Exception {
            return method.invoke(target, args);
        }
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
            Invocation invocation = new Invocation(target, method, args);
            return interceptor.intercept(invocation);
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
        public Object intercept(Invocation invocation) throws Exception {
            System.out.println("------1插入前置通知代码-------------");
            Object result = invocation.process();
            System.out.println("------1插入后置处理代码-------------");
            return result;
        }
    }

    public static class Log2Interceptor implements Interceptor {

        @Override
        public Object intercept(Invocation invocation) throws Exception {
            System.out.println("------2插入前置通知代码-------------");
            Object result = invocation.process();
            System.out.println("------2插入后置处理代码-------------");
            return result;
        }
    }

    public static void main(String[] args) {
        HelloService proxyService = (HelloService) Plugin.wrap(new HelloServiceImpl()
                , new LogInterceptor());
        proxyService = (HelloService) Plugin.wrap(proxyService, new Log2Interceptor());
        proxyService.sayHello();
    }
}
