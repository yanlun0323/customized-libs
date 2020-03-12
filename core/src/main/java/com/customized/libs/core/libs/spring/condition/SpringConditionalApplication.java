package com.customized.libs.core.libs.spring.condition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 在Spring Boot项目中，出现这个错误有两种情况：
 * <p>
 * 一、在main方法所在的类忘记添加@SpringBootApplication
 * <p>
 * 二缺少依赖，添加即可
 * <p>
 * <dependency>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-starter-web</artifactId>
 * </dependency>
 *
 * @author yan
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.customized.libs.libs.spring")
public class SpringConditionalApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringConditionalApplication.class, args);

        //bean自动注册，根据bean的名字获取Bean
        AmazonProperties amazonProperties = (AmazonProperties) context.getBean("amazonProperties");
        System.out.println(amazonProperties.getAssociateId());

        Boolean aBooleanWelcomeController = context.containsBean("welcomeController");
        if (!aBooleanWelcomeController) {
            System.out.println("        welcomeController init fail");
        } else {
            WelcomeController welcomeController = (WelcomeController) context.getBean("welcomeController");
            System.out.println("        welcomeController init Success");
        }

        Boolean aBooleanAmazonTest = context.containsBeanDefinition("amazonTest");
        if (!aBooleanAmazonTest) {
            System.out.println("        amazonTest init fail");
        } else {
            AmazonTest amazonTest = (AmazonTest) context.getBean("amazonTest");
            System.out.println("        amazonTest init Success");
        }
    }
}
