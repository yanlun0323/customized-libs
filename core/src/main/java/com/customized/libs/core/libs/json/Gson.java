package com.customized.libs.core.libs.json;

import com.customized.libs.core.libs.mapstruct.User;

import java.util.HashMap;
import java.util.Map;

public class Gson {

    public static void main(String[] args) {
        com.google.gson.Gson gson = new com.google.gson.Gson();

        Map<User, Integer> map = new HashMap<>(16);
        User user = new User("Java", 99);
        map.put(user, 99);

        System.out.println(gson.toJson(user));

        System.out.println(gson.toJson(map));
    }
}
