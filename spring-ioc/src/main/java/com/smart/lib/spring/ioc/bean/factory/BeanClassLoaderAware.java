package com.smart.lib.spring.ioc.bean.factory;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/18 13:58
 */
public interface BeanClassLoaderAware extends Aware {

    void setBeanClassLoader(ClassLoader classLoader);
}
