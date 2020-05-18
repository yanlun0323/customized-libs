package com.customized.libs.customer.starter;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.nacos.api.exception.NacosException;
import com.customized.libs.customer.config.DubboNacosConfig;
import com.customized.libs.customer.service.CommonDubboInvokerService;
import com.customized.libs.dubbo.api.utils.ExecutorsPool;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

/**
 * PropertySource提供外部化配置功能
 *
 * @author yan
 */
@EnableDubbo
@PropertySource(value = "classpath:/consumer-config.properties")
@SuppressWarnings("all")
public class ServiceConsumerBootstrap {

    private static final Integer MAX_INVOKE_TIMES = 0;

    public static void main(String[] args) throws NacosException, InterruptedException, IOException {
        DubboNacosConfig.init();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ServiceConsumerBootstrap.class);
        context.register(CommonDubboInvokerService.class);
        context.register(DubboNacosConfig.class);

        context.refresh();

        System.out.println("Service Consumer Is Starting...");

        CommonDubboInvokerService invokerService = context.getBean(CommonDubboInvokerService.class);

        // 多线程调用的方式，方便查看瞬时QPS
        for (int i = 0; i < MAX_INVOKE_TIMES; i++) {
            Thread.sleep(RandomUtils.nextLong(100L, 2000L));
            ExecutorsPool.FIXED_EXECUTORS.submit(() -> invokerService.invoke());
        }

        System.in.read();
    }
}