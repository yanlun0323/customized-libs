package com.smart.lib.spring.ioc.bean.factory.config;

import com.smart.lib.spring.ioc.bean.exception.BeansException;
import com.smart.lib.spring.ioc.bean.factory.ListableBeanFactory;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/16 13:53
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, ConfigurableBeanFactory, AutowireCapableBeanFactory {


    BeanDefinition getBeanDefinition(String beanName);

    /**
     * Ensure that all non-lazy-init singletons are instantiated, also considering
     * {@link org.springframework.beans.factory.FactoryBean FactoryBeans}.
     * Typically invoked at the end of factory setup, if desired.
     *
     * @throws BeansException if one of the singleton beans could not be created.
     *                        Note: This may have left the factory with some beans already initialized!
     *                        Call {@link #destroySingletons()} for full cleanup in this case.
     * @see #destroySingletons()
     */
    void preInstantiateSingletons() throws BeansException;
}
