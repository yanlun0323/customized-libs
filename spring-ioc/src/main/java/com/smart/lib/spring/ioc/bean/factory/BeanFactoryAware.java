package com.smart.lib.spring.ioc.bean.factory;

import com.smart.lib.spring.ioc.bean.exception.BeansException;

public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
