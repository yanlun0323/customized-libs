package com.customized.libs.core.libs.aop;

import com.alipay.sofa.common.utils.StringUtil;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.lang3.RandomStringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/3/2 15:26
 */
public class CglibDynamicTest {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setInterfaces(new Class[]{UserService.class});
        enhancer.setSuperclass(UserServiceImpl.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println(method.getName() + ", args: " + Arrays.toString(args));
                return proxy.invokeSuper(obj, args);
            }
        });

        UserService userService = (UserService) enhancer.create();
        userService.handleUserAge(10).handleUserAge(20);
        System.out.println(userService.getAge());
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
}
