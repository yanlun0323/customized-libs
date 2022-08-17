package com.smart.lib.spring.ioc.bean.context;

import com.smart.lib.spring.ioc.bean.exception.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 容齐刷新，提供bean扫描注册等功能
     */
    void refresh() throws BeansException;
}
