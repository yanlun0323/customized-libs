package com.customized.libs.core.libs.spring.components.scanner.test;

import com.customized.libs.core.libs.spring.components.scanner.EnableMyComponentScan;
import com.customized.libs.core.libs.spring.components.scanner.test.service.UserService;
import com.customized.libs.core.libs.spring.components.scanner.test.service.UserSessionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author yan
 */
@SpringBootApplication
@EnableMyComponentScan(scanBasePackages = "com.customized.libs.core.libs.spring.components.scanner.test.service")
public class SpringBootComponentScanStarterTest {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootComponentScanStarterTest.class);

        context.getBean(UserService.class).hello("Hello World!!");
        context.getBean(UserSessionService.class).getUserSession();
    }
}
