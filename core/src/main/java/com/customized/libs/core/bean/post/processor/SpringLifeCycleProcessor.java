package com.customized.libs.core.bean.post.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author yan
 */
@Component
public class SpringLifeCycleProcessor implements BeanPostProcessor {

    private final static Logger LOGGER = LoggerFactory.getLogger(SpringLifeCycleProcessor.class);

    /**
     * 预初始化 初始化之前调用
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        LOGGER.warn("SpringLifeCycleProcessor start beanName={}", beanName);
        return bean;
    }

    /**
     * 后初始化  bean 初始化完成调用
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        LOGGER.warn("SpringLifeCycleProcessor end beanName={}", beanName);
        return bean;
    }
}
