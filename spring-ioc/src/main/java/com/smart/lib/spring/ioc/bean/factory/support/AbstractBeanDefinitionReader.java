package com.smart.lib.spring.ioc.bean.factory.support;

import com.smart.lib.spring.ioc.bean.io.DefaultResourceLoader;
import com.smart.lib.spring.ioc.bean.io.ResourceLoader;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/16 10:12
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private final BeanDefinitionRegistry registry;
    private final ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
        this.resourceLoader = new DefaultResourceLoader();
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
