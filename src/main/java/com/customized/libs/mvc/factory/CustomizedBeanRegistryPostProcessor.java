package com.customized.libs.mvc.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;

/**
 * @author yan
 */
public class CustomizedBeanRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private String basePackage;

    private Class<? extends Annotation> annotationType;

    /**
     * 指定Spring扫描的类定义（配合spring-mvc的bean定义，自定义扫描）
     *
     * @param registry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        CustomizedClassPathMapperScanner scanner =
                new CustomizedClassPathMapperScanner(registry, annotationType);
        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage,
                ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void setAnnotationType(Class<? extends Annotation> annotationType) {
        this.annotationType = annotationType;
    }
}
