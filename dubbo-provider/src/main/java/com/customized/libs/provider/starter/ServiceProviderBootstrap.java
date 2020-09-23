package com.customized.libs.provider.starter;

import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallbackRegistry;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.nacos.api.exception.NacosException;
import com.customized.libs.provider.config.DubboNacosConfig;
import com.customized.libs.provider.dubbo.CustomizedDubboFallback;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

/**
 * @author yan
 */
@EnableDubbo(scanBasePackages = "com.customized.libs.provider.dubbo.impl")
@ComponentScan(value = {"com.customized.libs.provider"})
@PropertySource(value = "classpath:/provider-config.properties")
public class ServiceProviderBootstrap {

    public static void main(String[] args) throws IOException, NacosException {

        // Spring cglib生成的代理类

        // System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "./generator/spring-cglib");

        // Dubbo javassit生成的代理类

        // CtClass.debugDump = "./generator/dubbo-javassist";


        DubboNacosConfig.init();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ServiceProviderBootstrap.class);
        context.register(com.alipay.sofa.tracer.plugins.zipkin.initialize.ZipkinReportRegisterBean.class);
        context.refresh();

        DubboFallbackRegistry.setProviderFallback(new CustomizedDubboFallback());

        System.out.println("Service Provider Is Starting...");

        System.in.read();
    }
}