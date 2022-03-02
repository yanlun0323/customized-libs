package com.customized.libs.core.libs.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/3/2 15:26
 */
public class JdkDynamicTest {

    public static void main(String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Object proxyInstance = Proxy.newProxyInstance(classLoader, new Class[]{UserService.class},
                new UserServiceInvocationHandler(new UserServiceImpl()));
        UserService userService = (UserService) proxyInstance;
        userService.getUser();
    }

    public interface UserService {
        String getUser();
    }

    public static class UserServiceImpl implements UserService {

        @Override
        public String getUser() {
            return "User:Yan";
        }
    }

    public static class UserServiceInvocationHandler implements InvocationHandler {

        private final Object target;

        public UserServiceInvocationHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Execute User Service Invocation Handler!!");

            System.out.println(proxy.getClass().getName());
            System.out.println(target.getClass().getName());

            Object rt = method.invoke(target, args);
            System.out.println("获取结果 ==》 " + rt.toString());
            return rt;
        }
    }
}
