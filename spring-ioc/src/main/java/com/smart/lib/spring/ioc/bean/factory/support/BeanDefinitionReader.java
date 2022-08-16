package com.smart.lib.spring.ioc.bean.factory.support;

import com.smart.lib.spring.ioc.bean.exception.BeanException;
import com.smart.lib.spring.ioc.bean.io.Resource;
import com.smart.lib.spring.ioc.bean.io.ResourceLoader;

public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeanException;

    void loadBeanDefinitions(Resource... resources) throws BeanException;

    void loadBeanDefinitions(String location) throws BeanException;
}
