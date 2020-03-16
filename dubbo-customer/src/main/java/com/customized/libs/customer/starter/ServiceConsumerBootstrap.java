package com.customized.libs.customer.starter;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.nacos.api.exception.NacosException;
import com.customized.libs.customer.config.DubboNacosConfig;
import com.customized.libs.customer.service.CommonDubboInvokerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;

/**
 * @author yan
 */
@EnableDubbo
@PropertySource(value = "classpath:/consumer-config.properties")
public class ServiceConsumerBootstrap {

    public static void main(String[] args) throws NacosException {
        DubboNacosConfig.init();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ServiceConsumerBootstrap.class);
        context.register(CommonDubboInvokerService.class);
        context.register(DubboNacosConfig.class);

        context.refresh();

        CommonDubboInvokerService invokerService = context.getBean(CommonDubboInvokerService.class);
        invokerService.invoke();
        context.close();
    }
}