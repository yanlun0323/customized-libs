package com.customized.libs.core.libs.spring.autowired;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * @author yan
 */
public class SpringAutowiredTest implements EnvironmentAware, BeanFactoryAware {

    private BeanFactory beanFactory;

    private Environment environment;

    @PostConstruct
    public void setup() {
        System.out.println(this.beanFactory.getClass().getSimpleName());
        System.out.println(JSON.toJSONString(this.environment.getActiveProfiles()));
        System.out.println(JSON.toJSONString(this.environment.getDefaultProfiles()));

        ConfigurationPropertyName.of("xxxx");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
