package com.smart.lib.spring.ioc.bean.test;

import lombok.Data;

import java.util.Map;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/15 10:50
 */
@Data
public class UserServiceImpl {

    private String name;

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
}
