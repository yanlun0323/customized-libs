package com.smart.lib.spring.ioc.bean.test;

import com.smart.lib.spring.ioc.bean.annotations.Component;

import java.util.Map;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/15 10:50
 */
@Component
public class UserServiceImpl {

    private String name;

    private String company;

    private UserDaoImpl userDao;

    public UserServiceImpl() {

    }

    public UserServiceImpl(String name) {
        this.name = name;
    }

    public String queryName() {
        return this.name;
    }

    public Map<String, Object> getAllUser() {
        return this.userDao.getAllUser();
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

    public UserDaoImpl getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
