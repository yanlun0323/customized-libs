package com.smart.lib.spring.ioc.bean.factory.support;

import com.smart.lib.spring.ioc.bean.exception.BeansException;
import com.smart.lib.spring.ioc.bean.factory.config.BeanDefinition;
import com.smart.lib.spring.ioc.bean.factory.config.BeanPostProcessor;
import com.smart.lib.spring.ioc.bean.factory.config.ConfigurableBeanFactory;
import com.smart.lib.spring.ioc.bean.utils.BeanNameUtils;
import com.smart.lib.spring.ioc.bean.utils.ClassUtils;

import java.util.List;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/15 11:11
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = (beanClassLoader != null ? beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> beanClass, Object... args) {
        return (T) getBean(BeanNameUtils.translateBeanName(beanClass), args);
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

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object creatBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    protected abstract List<BeanPostProcessor> getBeanPostProcessors();


}
