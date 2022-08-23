package com.smart.lib.spring.ioc.bean.test;

import com.smart.lib.spring.ioc.bean.context.support.ClassPathXmlApplicationContext;
import com.smart.lib.spring.ioc.bean.test.event.CustomEvent;

import java.util.Map;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/17 10:39
 */
public class UserServiceImplClassPathXmlApplicationContextTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-beans.xml");
        UserServiceImpl service = applicationContext.getBean(UserServiceImpl.class);
        service.hello();
        System.out.println(service.getCompany());
        System.out.println(service.getName());
        Map<String, Object> allUser = service.getAllUser();
        System.out.println("All users ==> " + allUser);


        CustomEvent event = new CustomEvent(UserServiceImplClassPathXmlApplicationContextTest.class, 1L, "Hello");
        applicationContext.publishEvent(event);
    }
}
