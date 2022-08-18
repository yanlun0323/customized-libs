package com.smart.lib.spring.ioc.bean.factory.config;

import com.smart.lib.spring.ioc.bean.beans.PropertyValues;
import lombok.Data;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/15 10:40
 */
@Data
public class BeanDefinition {

    private Class beanClass;
    private PropertyValues propertyValues;

    private String initMethodName;
    private String destroyMethodName;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }
}
