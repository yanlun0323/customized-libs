package com.smart.lib.spring.ioc.bean.factory.support;

import com.smart.lib.spring.ioc.bean.factory.config.BeanDefinition;
import com.smart.lib.spring.ioc.bean.exception.BeansException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author yan
 * @version 1.0
 * @description JDK默认初始化策略
 * @date 2022/8/15 14:43
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {

    /**
     * 首先通过 beanDefinition 获取 Class 信息，这个 Class 信息是在 Bean 定义的时候传递进去的。
     * <p>
     * 接下来判断 ctor 是否为空，如果为空则是无构造函数实例化，否则就是需要有构造函数的实例化。
     * <p>
     * 这里我们重点关注有构造函数的实例化，实例化方式为 clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
     * <p>
     * 把入参信息传递给 newInstance 进行实例化。
     *
     * @param beanDefinition
     * @param beanName
     * @param ctor
     * @param args
     * @return
     * @throws BeansException
     */
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object instantiate(BeanDefinition beanDefinition, String beanName
            , Constructor<?> ctor, Object[] args) throws BeansException {
        Class clazz = beanDefinition.getBeanClass();
        try {
            if (ctor != null) {
                return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            } else {
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            throw new BeansException("Failed to instantiate [" + clazz.getName() + "]");
        }
    }
}
