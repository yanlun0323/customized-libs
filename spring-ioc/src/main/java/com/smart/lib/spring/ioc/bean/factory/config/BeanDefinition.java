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

    /**
     * Scope identifier for the standard singleton scope: "singleton".
     * <p>Note that extended bean factories might support further scopes.
     *
     * @see #setScope
     */
    public static final String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;

    /**
     * Scope identifier for the standard prototype scope: "prototype".
     * <p>Note that extended bean factories might support further scopes.
     *
     * @see #setScope
     */
    public static final String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

    private Class beanClass;
    private PropertyValues propertyValues;

    private String initMethodName;
    private String destroyMethodName;

    private String scope = SCOPE_SINGLETON;

    /**
     * singleton、prototype，是本次在 BeanDefinition 类中新增加的两个属性信息，
     * <p>
     * 用于把从 spring.xml 中解析到的 Bean 对象作用范围填充到属性中。singleton、prototype，
     * 是本次在 BeanDefinition 类中新增加的两个属性信息，用于把从 spring.xml 中解析到的 Bean 对象作用范围填充到属性中。
     */
    private boolean singleton = true;
    private boolean prototype = false;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }
}
