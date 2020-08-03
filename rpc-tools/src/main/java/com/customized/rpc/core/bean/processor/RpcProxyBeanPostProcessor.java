package com.customized.rpc.core.bean.processor;

import com.customized.rpc.client.RpcClient;
import com.customized.rpc.client.RpcProxyFactory;
import com.customized.rpc.core.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Bean的后置处理器，用于注入Rpc动态代理对象
 */
public class RpcProxyBeanPostProcessor implements BeanPostProcessor {

    private static Logger log = LoggerFactory.getLogger(RpcProxyBeanPostProcessor.class);

    private final Map<Class<?>, Object> cache = new HashMap<>(128);

    private RpcClient client;

    public RpcProxyBeanPostProcessor(RpcClient client) {
        this.client = client;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 遍历所有字段
        for (Field field : bean.getClass().getDeclaredFields()) {
            // 判断是否有@Reference注解
            if (field.isAnnotationPresent(Reference.class)) {
                field.setAccessible(Boolean.TRUE);
                Class<?> clazz = field.getType();
                Object proxy = null;

                if (cache.containsKey(clazz)) {
                    proxy = cache.get(clazz);
                } else {
                    // 动态生成代理对象
                    proxy = new RpcProxyFactory<>(client, clazz).getProxyObject();
                    cache.put(clazz, proxy);
                }

                try {
                    field.set(bean, proxy);
                } catch (IllegalAccessException e) {
                    log.error("==> Reference " + field + " Inject Failed!!", e);
                }
            }
        }
        return bean;
    }
}
