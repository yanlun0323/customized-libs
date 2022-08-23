package com.smart.lib.spring.ioc.bean.test;

import com.smart.lib.spring.ioc.bean.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yan
 * @version 1.0
 * @description DAO模拟
 * @date 2022/8/15 15:32
 */
public class UserDaoProxy implements FactoryBean<UserDao> {

    private final Map<String, Object> users = new HashMap<>();

    public void initData() {
        System.out.println(this.getClass().getSimpleName() + " 执行：init-method方法");
        users.put("001", "JAVA");
        users.put("002", "IDEA");
    }

    public void destroy() {
        System.out.println(this.getClass().getSimpleName() + " 执行：destroy-method方法");
        users.clear();
    }

    @Override
    public UserDao getObject() {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("getAllUsers")) {
                    return users;
                } else if (method.getName().equals("queryUserName")) {
                    return "你被代理了 " + method.getName() + "：" + users.get(args[0].toString());
                }
                throw new RuntimeException("unsupported method(" + method.getName() + ")");
            }
        };
        return (UserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{UserDao.class}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return UserDao.class;
    }
}
