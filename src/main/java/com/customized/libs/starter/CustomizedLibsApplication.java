package com.customized.libs.starter;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.core.DebuggingClassWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


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
public class CustomizedLibsApplication {

    // private static final Logger LOGGER = LogManager.getLogger(AnalyzeApplication.class);

    public static void main(String[] args) {

        // System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "./libs/proxy-target");


        log.trace("log4j trace log");
        log.debug("log4j debug log");
        log.info("log4j info log");
        log.warn("log4j warn log");

        log.error("log4j error log", new RuntimeException("Runtime Exception"));

        SpringApplication.run(CustomizedLibsApplication.class, args);
    }
}
