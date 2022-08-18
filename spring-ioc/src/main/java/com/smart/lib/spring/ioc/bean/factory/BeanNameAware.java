package com.smart.lib.spring.ioc.bean.factory;

public interface BeanNameAware extends Aware {

    void setBeanName(String beanName);
}
