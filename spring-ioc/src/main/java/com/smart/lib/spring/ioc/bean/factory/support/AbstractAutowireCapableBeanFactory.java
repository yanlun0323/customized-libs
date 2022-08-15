package com.smart.lib.spring.ioc.bean.factory.support;

import com.smart.lib.spring.ioc.bean.beans.PropertyValue;
import com.smart.lib.spring.ioc.bean.beans.PropertyValues;
import com.smart.lib.spring.ioc.bean.exception.BeanException;
import com.smart.lib.spring.ioc.bean.factory.config.BeanDefinition;
import com.smart.lib.spring.ioc.bean.factory.config.BeanReference;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author yan
 * @version 1.0
 * @description bean自动注入
 * @date 2022/8/15 11:14
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();


    @Override
    protected Object creatBean(String beanName, BeanDefinition beanDefinition, Object[] args)
            throws BeanException {
        Object bean;
        try {
            bean = doCreateBeanInstance(beanName, beanDefinition, args);
            // 给 Bean 填充属性
            applyPropertyValues(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeanException("Instantiation of bean failed");
        }
        this.addSingleton(beanName, bean);
        return bean;
    }

    @SuppressWarnings("rawtypes")
    private Object doCreateBeanInstance(String beanName, BeanDefinition beanDefinition, Object[] args) {
        Constructor constructorToUse = null;
        if (args != null && args.length > 0) {
            Class<?> beanClass = beanDefinition.getBeanClass();
            Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
            for (Constructor<?> declaredConstructor : declaredConstructors) {
                if (args.length == declaredConstructor.getParameterCount()) {
                    constructorToUse = declaredConstructor;
                }
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
    }

    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            // 普通属性填充和引用对象填充
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            try {
                if (value instanceof BeanReference) {
                    // 不考虑循环依赖
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }
                BeanUtils.setProperty(bean, name, value);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new BeanException("Error setting property values：" + beanName);
            }
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
