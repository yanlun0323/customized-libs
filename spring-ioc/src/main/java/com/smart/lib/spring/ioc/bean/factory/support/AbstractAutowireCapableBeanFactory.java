package com.smart.lib.spring.ioc.bean.factory.support;

import cn.hutool.core.util.StrUtil;
import com.smart.lib.spring.ioc.bean.beans.PropertyValue;
import com.smart.lib.spring.ioc.bean.beans.PropertyValues;
import com.smart.lib.spring.ioc.bean.exception.BeansException;
import com.smart.lib.spring.ioc.bean.factory.*;
import com.smart.lib.spring.ioc.bean.factory.config.AutowireCapableBeanFactory;
import com.smart.lib.spring.ioc.bean.factory.config.BeanDefinition;
import com.smart.lib.spring.ioc.bean.factory.config.BeanPostProcessor;
import com.smart.lib.spring.ioc.bean.factory.config.BeanReference;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yan
 * @version 1.0
 * @description bean自动注入
 * @date 2022/8/15 11:14
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();


    @Override
    protected Object creatBean(String beanName, BeanDefinition beanDefinition, Object[] args)
            throws BeansException {
        Object bean;
        try {
            bean = doCreateBeanInstance(beanName, beanDefinition, args);
            // 给 Bean 填充属性
            applyPropertyValues(beanName, bean, beanDefinition);
            // 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed");
        }
        // 注册实现了 DisposableBean 接口的 Bean 对象
        this.registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        this.addSingleton(beanName, bean);
        return bean;
    }

    public void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (bean instanceof DisposableBean
                || StrUtil.isNotBlank(beanDefinition.getDestroyMethodName())) {
            this.registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
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
                throw new BeansException("Error setting property values：" + beanName);
            }
        }
    }

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // Aware感知
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }
        // 1. 执行 BeanPostProcessor Before 处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        // 待完成内容：invokeInitMethods(beanName, wrappedBean, beanDefinition);
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception exception) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed");
        }

        // 2. 执行 BeanPostProcessor After 处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }

    @SuppressWarnings("unchecked")
    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        // 1. 实现接口 InitializingBean
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
        // 2. 配置信息 init-method
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotBlank(initMethodName)
                && !(bean instanceof InitializingBean && "afterPropertiesSet".equals(initMethodName))) {
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            if (initMethod == null) {
                // 此种方式getMethod直接抛出了异常
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
            initMethod.invoke(bean);
        }
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (null == current) return result;
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (null == current) return result;
            result = current;
        }
        return result;
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
