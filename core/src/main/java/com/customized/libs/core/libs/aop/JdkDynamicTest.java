package com.customized.libs.core.libs.aop;

import com.alipay.sofa.common.utils.StringUtil;
import org.apache.commons.lang3.RandomStringUtils;

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
        System.out.println("UserName ==> " + userService.getUser());

        userService.handleUserAge(10).handleUserAge(20);
        System.out.println("UserAge ==> " + userService.getAge());
    }

    public interface UserService {
        UserService getUserService();

        String getUser();

        String getAge();

        UserService handleUserAge(Integer age);
    }

    public static class UserServiceImpl implements UserService {

        private String age;

        @Override
        public UserService getUserService() {
            return this;
        }

        @Override
        public String getUser() {
            return RandomStringUtils.randomAlphabetic(16);
        }

        @Override
        public String getAge() {
            return this.age;
        }

        @Override
        public UserService handleUserAge(Integer age) {
            if (StringUtil.isNotBlank(this.age)) {
                this.age += "\r\n" + String.format("User: %s, Age: %s!", this.getUser(), age);
            } else {
                this.age = String.format("\r\nUser: %s, Age: %s!", this.getUser(), age);
            }
            return this;
        }
    }

    public static class UserServiceInvocationHandler implements InvocationHandler {

        private final UserService target;

        public UserServiceInvocationHandler(UserService target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("handleUserAge".equals(method.getName())) {
                target.handleUserAge((Integer) args[0]);
                return proxy;
            }
            System.out.println("Execute User Service Invocation Handler!!");

            // 为什么这里有一个proxy对象？
            System.out.println(proxy.getClass().getName());
            System.out.println(target.getClass().getName());

            Object rt = method.invoke(target, args);
            System.out.println("获取结果 ==》 " + rt.toString());
            return rt;
        }
    }
}
