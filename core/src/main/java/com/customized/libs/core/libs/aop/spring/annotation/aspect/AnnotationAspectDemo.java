package com.customized.libs.core.libs.aop.spring.annotation.aspect;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/3/3 11:21
 */
@EnableAspectJAutoProxy(exposeProxy = true)
public class AnnotationAspectDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.customized.libs.core.libs.aop.spring.annotation.aspect");
        context.registerBean(AnnotationAspectDemo.class);

        // 需要refresh激活容器实例化bean
        context.refresh();
        SomethingServiceImpl bean = context.getBean(SomethingServiceImpl.class);
        bean.method0();
        System.out.println("=========");
        bean.method1();
    }
}
