package com.smart.lib.spring.ioc.bean.factory;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/15 10:40
 */
public interface BeanFactory {

    <T> T getBean(Class<T> beanClass, Object... args);

    Object getBean(String beanName, Object... args);
}
