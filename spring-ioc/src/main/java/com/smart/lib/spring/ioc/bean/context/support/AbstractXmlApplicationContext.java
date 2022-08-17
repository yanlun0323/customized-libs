package com.smart.lib.spring.ioc.bean.context.support;

import com.smart.lib.spring.ioc.bean.factory.DefaultListableBeanFactory;
import com.smart.lib.spring.ioc.bean.factory.support.XmlBeanDefinitionReader;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/17 10:18
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();
}
