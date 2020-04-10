package com.customized.libs.core.libs.spring.components.scanner.test;

import com.customized.libs.core.libs.spring.components.scanner.EnableMyComponentScan;
import com.customized.libs.core.libs.spring.components.scanner.expand.MyBeanProxyFactory;
import com.customized.libs.core.libs.spring.components.scanner.test.service.UserService;
import com.customized.libs.core.libs.spring.components.scanner.test.service.UserSessionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;

/**
 * @author yan
 */
@SpringBootApplication
@EnableMyComponentScan(scanBasePackages = "com.customized.libs.core.libs.spring.components.scanner.test.service")
@SuppressWarnings("unchecked")
public class SpringBootComponentScanStarterTest {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootComponentScanStarterTest.class);

        // 获取FactoryBean代理后的对象
        context.getBean(UserService.class).hello("Hello World!!");

        // 获取FactoryBean实际对象
        MyBeanProxyFactory<UserService> beanProxyFactory = (MyBeanProxyFactory) context.getBean("&userServiceImpl");
        Objects.requireNonNull(beanProxyFactory.getObject()).hello("Hello World!!");

        context.getBean(UserSessionService.class).getUserSession();
    }
}
