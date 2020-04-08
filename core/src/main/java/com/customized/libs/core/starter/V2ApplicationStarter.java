package com.customized.libs.core.starter;

import com.customized.libs.core.utils.SpringContextLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * 在Spring Boot项目中，出现这个错误有两种情况：
 * <p>
 * 一，在main方法所在的类忘记添加@SpringBootApplication
 * <p>
 * 二，缺少依赖，添加即可
 * <p>
 * <dependency>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-starter-web</artifactId>
 * </dependency>
 *
 * @author yan
 */
@SpringBootApplication(exclude = {
        MongoDataAutoConfiguration.class, MongoAutoConfiguration.class
})
@ComponentScan(basePackages = "com.customized.libs")
@Slf4j
@EnableScheduling
public class V2ApplicationStarter {

    public static void main(String[] args) {
        /*
         * 实际上SpringApplicationBuilder也是基于new SpringApplication()实现的，build方式提供链式调用结构。
         */
        ConfigurableApplicationContext context = new SpringApplicationBuilder(V2ApplicationStarter.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
        SpringContextLoader.init(context);
    }
}