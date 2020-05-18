package com.customized.libs.customer.starter;

import com.alibaba.dubbo.config.model.ApplicationModel;
import com.alibaba.dubbo.config.model.ConsumerModel;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.dubbo.rpc.service.EchoService;
import com.alibaba.nacos.api.exception.NacosException;
import com.customized.libs.customer.config.DubboNacosConfig;
import com.customized.libs.customer.service.CommonDubboInvokerService;
import com.customized.libs.dubbo.api.utils.ExecutorsPool;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

/**
 * PropertySource提供外部化配置功能
 *
 * @author yan
 */
@EnableDubbo(scanBasePackages = "com.customized.libs.customer.service")
@ComponentScan(value = {"com.customized.libs.customer"})
@PropertySource(value = "classpath:/consumer-config.properties")
@SuppressWarnings("all")
public class ServiceConsumerBootstrap {

    private static final Integer MAX_INVOKE_TIMES = 5;

    public static void main(String[] args) throws NacosException, InterruptedException, IOException {
        DubboNacosConfig.init();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        // 此处初始化有2种方案：
        // 1、context.scan；2、context.register

        // context.scan("com.customized.libs.customer");
        context.register(ServiceConsumerBootstrap.class);
        context.refresh();

        System.out.println("Service Consumer Is Starting...");

        CommonDubboInvokerService invokerService = (CommonDubboInvokerService) context.getBean("commonDubboInvokerService");

        System.out.println("Service Consumer get all Spring bean names begin...\r\n");
        context.getBeanFactory().getBeanNamesIterator().forEachRemaining(System.out::println);
        System.out.println("Service Consumer get all Spring bean names end...\r\n");


        // 通过ApplicationModel获取ConsumerModel对象，内部存储着Reference对象，实现了CommonDubboProvider及EchoService
        ConsumerModel consumerModel = ApplicationModel.
                getConsumerModel("com.customized.libs.dubbo.api.CommonDubboProvider:1.0");
        EchoService echoService = (EchoService) consumerModel.getProxyObject();
        System.out.println("Dubbo Consumer Echo Test ==> " + echoService.$echo("get!!"));

        // 多线程调用的方式，方便查看瞬时QPS
        for (int i = 0; i < MAX_INVOKE_TIMES; i++) {
            Thread.sleep(RandomUtils.nextLong(100L, 2000L));
            ExecutorsPool.FIXED_EXECUTORS.submit(() -> invokerService.invoke());
        }

        System.in.read();
    }
}