package com.smart.lib.spring.ioc.bean.factory.support;

import com.smart.lib.spring.ioc.bean.exception.BeansException;
import com.smart.lib.spring.ioc.bean.factory.FactoryBean;
import com.smart.lib.spring.ioc.bean.factory.config.BeanDefinition;
import com.smart.lib.spring.ioc.bean.factory.config.BeanPostProcessor;
import com.smart.lib.spring.ioc.bean.factory.config.ConfigurableBeanFactory;
import com.smart.lib.spring.ioc.bean.utils.BeanNameUtils;
import com.smart.lib.spring.ioc.bean.utils.ClassUtils;
import com.smart.lib.spring.ioc.bean.utils.StringValueResolver;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/15 11:11
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    /**
     * String resolvers to apply e.g. to annotation attribute values.
     */
    private final List<StringValueResolver> embeddedValueResolvers = new CopyOnWriteArrayList<>();

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = (beanClassLoader != null ? beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> beanClass, Object... args) {
        return (T) getBean(BeanNameUtils.translateBeanName(beanClass), args);
    }

    @Override
    public Object getBean(String beanName, Object... args) {
        return doGetBean(beanName, args);
    }

    @SuppressWarnings("unchecked")
    private <T> T doGetBean(final String beanName, Object[] args) {
        Object sharedInstance = getSingleton(beanName);
        if (sharedInstance != null) {
            return (T) getObjectForBeanInstance(sharedInstance, beanName);
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        Object bean = creatBean(beanName, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean, beanName);
    }


    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

        Object object = getCachedObjectForFactoryBean(beanName);

        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }
        return object;
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        if (value == null) {
            return null;
        }
        String result = value;
        for (StringValueResolver embeddedValueResolver : this.embeddedValueResolvers) {
            result = embeddedValueResolver.resolveStringValue(result);
            if (result == null) {
                return null;
            }
        }
        return result;
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object creatBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    protected abstract List<BeanPostProcessor> getBeanPostProcessors();

}
