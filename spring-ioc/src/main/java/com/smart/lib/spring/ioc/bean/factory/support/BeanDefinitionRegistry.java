package com.smart.lib.spring.ioc.bean.factory.support;

import com.smart.lib.spring.ioc.bean.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    boolean containsBeanDefinition(String beanName);
}
