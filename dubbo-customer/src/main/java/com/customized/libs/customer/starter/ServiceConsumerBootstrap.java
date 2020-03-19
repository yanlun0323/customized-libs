package com.customized.libs.customer.starter;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.nacos.api.exception.NacosException;
import com.customized.libs.customer.config.DubboNacosConfig;
import com.customized.libs.customer.service.CommonDubboInvokerService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.Executors;

/**
 * @author yan
 */
@EnableDubbo
@PropertySource(value = "classpath:/consumer-config.properties")
@SuppressWarnings("all")
public class ServiceConsumerBootstrap {

    public static void main(String[] args) throws NacosException, InterruptedException {
        DubboNacosConfig.init();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ServiceConsumerBootstrap.class);
        context.register(CommonDubboInvokerService.class);
        context.register(DubboNacosConfig.class);

        context.refresh();

        CommonDubboInvokerService invokerService = context.getBean(CommonDubboInvokerService.class);

        // 多线程调用的方式，方便查看瞬时QPS
        for (int i = 0; i < 2000; i++) {
            Thread.sleep(RandomUtils.nextLong(100L, 2000L));
            Executors.newFixedThreadPool(10).submit(() -> invokerService.invoke());
        }

        context.close();
    }
}