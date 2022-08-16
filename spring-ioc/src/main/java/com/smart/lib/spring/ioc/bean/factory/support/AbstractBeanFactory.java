package com.smart.lib.spring.ioc.bean.factory.support;

import com.smart.lib.spring.ioc.bean.exception.BeanException;
import com.smart.lib.spring.ioc.bean.factory.BeanFactory;
import com.smart.lib.spring.ioc.bean.factory.config.BeanDefinition;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/15 11:11
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> beanClass, Object... args) {
        return (T) getBean(beanClass.getSimpleName(), args);
    }

    @Override
    public Object getBean(String beanName, Object... args) {
        return doGetBean(beanName, args);
    }

    @SuppressWarnings("unchecked")
    private <T> T doGetBean(String beanName, Object[] args) {
        Object bean = getSingleton(beanName);
        if (bean != null) {
            return (T) bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return (T) creatBean(beanName, beanDefinition, args);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeanException;

    protected abstract Object creatBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeanException;
}
