package com.customized.libs.core.libs.json;

import com.alibaba.fastjson.JSON;
import com.customized.libs.core.libs.mapstruct.User;

import java.util.HashMap;
import java.util.Map;

public class FastJson {

    public static void main(String[] args) {
        Map<User, Integer> map = new HashMap<>(16);
        User user = new User("Java", 99);
        map.put(user, 99);


        System.out.println(JSON.toJSONString(user));
        System.out.println(JSON.toJSONString(map));
    }
}
