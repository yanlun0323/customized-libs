package com.smart.lib.spring.ioc.bean.factory.support;

public interface SingletonBeanRegistry {

    /**
     * 获取单例对象
     *
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);

    void destroySingletons();
}
