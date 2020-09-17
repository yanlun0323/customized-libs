package com.customized.libs.core.libs.json;

import com.customized.libs.core.libs.mapstruct.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class Jackson {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Map<User, Integer> map = new HashMap<>(16);
        User user = new User("Java", 99);
        map.put(user, 99);

        System.out.println(mapper.writeValueAsString(user));

        System.out.println(mapper.writeValueAsString(map));
    }
}
