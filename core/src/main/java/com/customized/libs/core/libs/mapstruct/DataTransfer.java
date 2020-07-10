package com.customized.libs.core.libs.mapstruct;

import com.alibaba.fastjson.JSON;
import com.customized.libs.core.libs.mapstruct.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

public class DataTransfer {

    public static void main(String[] args) {
        List<User> data = new ArrayList<>();

        User user0 = new User("Java", 1998);
        user0.setIndex(0);
        User user1 = new User("C", 1992);
        user1.setIndex(1);


        data.add(user0);
        data.add(user1);

        UserMapper mapper = UserMapper.INSTANCE;


        System.out.println("Single Bean Convert ==> " + JSON.toJSONString(mapper.transfer(user0)));

        System.out.println("Multiple Bean Convert ==> " + JSON.toJSONString(mapper.transfer(data)));
    }
}
