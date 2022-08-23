package com.smart.lib.spring.ioc.bean.factory.config;

import com.smart.lib.spring.ioc.bean.factory.HierarchicalBeanFactory;
import com.smart.lib.spring.ioc.bean.factory.support.SingletonBeanRegistry;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    void setBeanClassLoader(ClassLoader beanClassLoader);

    ClassLoader getBeanClassLoader();

}
