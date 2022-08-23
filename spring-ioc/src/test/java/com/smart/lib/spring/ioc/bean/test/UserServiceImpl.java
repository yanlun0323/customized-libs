package com.smart.lib.spring.ioc.bean.test;

import com.smart.lib.spring.ioc.bean.annotations.Component;
import com.smart.lib.spring.ioc.bean.context.ApplicationContext;
import com.smart.lib.spring.ioc.bean.context.ApplicationContextAware;
import com.smart.lib.spring.ioc.bean.context.annotation.Value;
import com.smart.lib.spring.ioc.bean.factory.DisposableBean;
import com.smart.lib.spring.ioc.bean.factory.InitializingBean;

import java.util.Map;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/15 10:50
 */
@Component
public class UserServiceImpl implements DisposableBean, InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Value("${user.name}")
    private String user;

    private String name;

    private String company;

    private UserDao userDao;

    public UserServiceImpl() {

    }

    public UserServiceImpl(String name) {
        this.name = name;
    }

    public String queryName() {
        return this.name;
    }

    public Map<String, Object> getAllUser() {
        UserDao bean = this.applicationContext.getBean(UserDao.class);
        System.out.println("applicationContext get bean ==> " + bean.getAllUsers());

        String s = bean.queryUserName("001");
        System.out.println(s);
        return this.userDao.getAllUsers();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void hello() {
        System.out.println("Hello " + this.name + "!");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println(this.getClass().getSimpleName() + " 执行：destroy-method方法");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this.getClass().getSimpleName() + " 执行：init-method方法");
    }

    @Override
    public void setApplicationContextAware(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
