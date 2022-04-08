package com.customized.libs.core.starter;

import com.customized.libs.core.libs.spring.bean.EnableBeanPostConfig;
import com.customized.libs.core.utils.SpringContextLoader;
import com.customized.multiple.versions.adapter.autoconfigure.EnableMultipleVersions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
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
/**
 * EnableAutoConfiguration依然依赖于ComponentScan组件的basePackages标记
 */
@ComponentScan(basePackages = {"com.customized.libs", "com.customized.multiple.versions"})
@Slf4j
@EnableScheduling
@EnableBeanPostConfig
@EnableAspectJAutoProxy
@EnableMultipleVersions
public class V2ApplicationStarter {

    public static void main(String[] args) {
        // System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "./libs/proxy-target");

        /*
         * 实际上SpringApplicationBuilder也是基于new SpringApplication()实现的，build方式提供链式调用结构。
         */
        ConfigurableApplicationContext context = new SpringApplicationBuilder(V2ApplicationStarter.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
        SpringContextLoader.init(context);

        WebServer webServer = ((ServletWebServerApplicationContext) context).getWebServer();
        System.out.println(" ==> " + webServer.toString());
        // webServer.stop();
    }
}
