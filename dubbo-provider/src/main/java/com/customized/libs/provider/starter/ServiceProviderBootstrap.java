package com.customized.libs.provider.starter;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.nacos.api.exception.NacosException;
import com.customized.libs.provider.config.DubboNacosConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

/**
 * @author yan
 */
@EnableDubbo(scanBasePackages = "com.customized.libs.provider.dubbo.impl")
@PropertySource(value = "classpath:/provider-config.properties")
public class ServiceProviderBootstrap {

    public static void main(String[] args) throws IOException, NacosException {
        DubboNacosConfig.init();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ServiceProviderBootstrap.class);
        context.register(DubboNacosConfig.class);
        context.refresh();

        System.out.println("Service Provider Is Starting...");

        System.in.read();
    }
}