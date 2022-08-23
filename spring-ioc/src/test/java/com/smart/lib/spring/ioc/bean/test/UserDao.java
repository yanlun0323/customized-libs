package com.smart.lib.spring.ioc.bean.test;

import java.util.Map;

public interface UserDao {

    String queryUserName(String uid);

    Map<String, Object> getAllUsers();
}
