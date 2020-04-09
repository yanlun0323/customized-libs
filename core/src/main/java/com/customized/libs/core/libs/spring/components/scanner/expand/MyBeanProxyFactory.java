package com.customized.libs.core.libs.spring.components.scanner.expand;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @author yan
 */
public class MyBeanProxyFactory<T> implements FactoryBean<T> {

    private T target;

    private Class<T> targetInterface;

    private MyBeanProxyHandler<T> beanProxy;

    @Override
    public T getObject() {
        return this.newInstance(this.beanProxy);
    }

    @Override
    public Class<?> getObjectType() {
        return this.targetInterface;
    }

    @SuppressWarnings("unchecked")
    private T newInstance(MyBeanProxyHandler<T> beanProxy) {
        beanProxy.setTargetImpl(target);
        return (T) Proxy.newProxyInstance(this.targetInterface.getClassLoader(), this.targetInterface.getInterfaces(), beanProxy);
    }

    public void setBeanProxy(MyBeanProxyHandler<T> beanProxy) {
        this.beanProxy = beanProxy;
    }

    public void setTargetInterface(Class<T> targetInterface) {
        this.targetInterface = targetInterface;
    }

    public void setTarget(T target) {
        this.target = target;
    }
}
