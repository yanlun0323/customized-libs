package com.smart.lib.spring.ioc.bean.factory.support;

import cn.hutool.core.util.StrUtil;
import com.smart.lib.spring.ioc.bean.exception.BeansException;
import com.smart.lib.spring.ioc.bean.factory.DisposableBean;
import com.smart.lib.spring.ioc.bean.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/18 11:11
 */
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;
    private final String beanName;
    private final String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }
        if (StrUtil.isNotBlank(destroyMethodName)
                && !(bean instanceof DisposableBean && "destroy".equals(destroyMethodName))) {
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            if (null == destroyMethod) {
                throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
            }
            destroyMethod.invoke(bean);
        }
    }
}
