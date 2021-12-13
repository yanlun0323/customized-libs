package com.customized.libs.core.libs.mybatis.plugin.dynamic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyDynamicProxyV5 {

    public interface HelloService {
        void sayHello();
    }

    public interface ILoveYouService {
        void sayILoveYou();
    }

    public static class HelloServiceImpl implements HelloService, ILoveYouService {

        @Override
        public void sayHello() {
            System.out.println("Say Hello!!");
        }

        @Override
        public void sayILoveYou() {
            System.out.println("I Love You!");
        }
    }

    public interface Interceptor {
        /**
         * 具体拦截处理
         */
        Object intercept(Invocation invocation) throws Exception;

        /**
         * 插入目标类
         */
        Object plugin(Object target);
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

        private final Object target;
        private final Interceptor interceptor;

        public Plugin(Object target, Interceptor interceptor) {
            this.target = target;
            this.interceptor = interceptor;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Invocation invocation = new Invocation(target, method, args);
            return interceptor.intercept(invocation);
        }

        /**
         * Mybatis这里根据注解Intercepts解析Interceptor
         *
         * @param target
         * @param interceptor
         * @return
         */
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

        @Override
        public Object plugin(Object target) {
            return Plugin.wrap(target, this);
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

        @Override
        public Object plugin(Object target) {
            return Plugin.wrap(target, this);
        }
    }

    public static class InterceptorChain {

        private final List<Interceptor> interceptorList = new ArrayList<>();

        /**
         * 插入所有拦截器
         */
        public Object pluginAll(Object target) {
            for (Interceptor interceptor : interceptorList) {
                target = interceptor.plugin(target);
            }
            return target;
        }

        public void addInterceptor(Interceptor interceptor) {
            interceptorList.add(interceptor);
        }

        /**
         * 返回一个不可修改集合，只能通过addInterceptor方法添加
         * 这样控制权就在自己手里
         */
        public List<Interceptor> getInterceptorList() {
            return Collections.unmodifiableList(interceptorList);
        }
    }

    public static void main(String[] args) {
        HelloService proxyService = new HelloServiceImpl();
        InterceptorChain chain = new InterceptorChain();
        chain.addInterceptor(new LogInterceptor());
        chain.addInterceptor(new Log2Interceptor());

        proxyService = (HelloService) chain.pluginAll(proxyService);

        proxyService.sayHello();

        System.out.println("\r\n------------分隔线------------\r\n");

        ILoveYouService iLoveYouService = (ILoveYouService) proxyService;
        iLoveYouService.sayILoveYou();
    }
}
