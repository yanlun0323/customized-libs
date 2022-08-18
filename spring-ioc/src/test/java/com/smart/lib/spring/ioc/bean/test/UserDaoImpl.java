package com.smart.lib.spring.ioc.bean.test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yan
 * @version 1.0
 * @description DAO模拟
 * @date 2022/8/15 15:32
 */
public class UserDaoImpl {

    private final Map<String, Object> users = new HashMap<>();

    public void initData() {
        users.put("001", "JAVA");
        users.put("002", "IDEA");
    }

    public void destroy() {
        users.clear();
    }

    public Map<String, Object> getAllUser() {
        return users;
    }
}
