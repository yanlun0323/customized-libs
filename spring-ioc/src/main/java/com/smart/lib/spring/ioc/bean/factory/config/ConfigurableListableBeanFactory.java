package com.smart.lib.spring.ioc.bean.factory.config;

import com.smart.lib.spring.ioc.bean.factory.ListableBeanFactory;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/16 13:53
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, ConfigurableBeanFactory, AutowireCapableBeanFactory {


    BeanDefinition getBeanDefinition(String beanName);
}
