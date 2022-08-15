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

    public Map<String, Object> getAllUser() {
        Map<String, Object> users = new HashMap<>();
        users.put("001", "JAVA");
        users.put("002", "IDEA");
        return users;
    }
}
