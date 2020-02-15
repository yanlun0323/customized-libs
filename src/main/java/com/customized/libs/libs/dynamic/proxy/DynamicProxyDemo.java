package com.customized.libs.libs.dynamic.proxy;

import com.customized.libs.libs.dynamic.proxy.mapper.MyMapper;

public class DynamicProxyDemo {

    public static void main(String[] args) {

        MapperRegistry registry = new MapperRegistry();
        registry.addMapper(MyMapper.class);

        MyMapper target = registry.getMapper(MyMapper.class, new SqlSession());
        String user = target.getUser();
        System.out.println(user);

        MyMapper target0 = registry.getMapper(MyMapper.class, new SqlSession());
        String user0 = target0.getUser();
        System.out.println(user0);
    }
}
