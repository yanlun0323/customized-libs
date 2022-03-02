package com.customized.libs.core.libs.aop.spring;

import com.customized.libs.core.libs.aop.spring.biz.OrderService;
import com.customized.libs.core.libs.aop.spring.biz.impl.UserServiceImplZZ;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/2/28 16:23
 */
public class SpringAopXmlTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-aop.xml");
        OrderService orderService = context.getBean(OrderService.class);
        String order101 = orderService.queryOrder("Order101");
        System.out.println("结果 ==》" + order101);

        UserServiceImplZZ userService = context.getBean(UserServiceImplZZ.class);
        System.out.println("User ==》" + userService.getUser());
    }
}
