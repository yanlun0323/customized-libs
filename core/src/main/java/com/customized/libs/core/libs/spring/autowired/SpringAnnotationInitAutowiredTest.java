package com.customized.libs.core.libs.spring.autowired;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author yan
 */
public class SpringAnnotationInitAutowiredTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringAutowiredTest.class);
        context.refresh();

        SpringAutowiredTest service = context.getBean(SpringAutowiredTest.class);

        System.out.println(service);
    }
}
