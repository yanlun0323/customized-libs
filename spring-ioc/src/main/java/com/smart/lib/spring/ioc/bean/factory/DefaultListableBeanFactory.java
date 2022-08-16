package com.smart.lib.spring.ioc.bean.factory;

import com.smart.lib.spring.ioc.bean.exception.BeanException;
import com.smart.lib.spring.ioc.bean.factory.config.BeanDefinition;
import com.smart.lib.spring.ioc.bean.factory.config.ConfigurableListableBeanFactory;
import com.smart.lib.spring.ioc.bean.factory.support.AbstractAutowireCapableBeanFactory;
import com.smart.lib.spring.ioc.bean.factory.support.BeanDefinitionRegistry;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yan
 * @version 1.0
 * @description 默认实现
 * @date 2022/8/15 10:45
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
        implements ConfigurableListableBeanFactory, BeanDefinitionRegistry {


    /**
     * Map of bean definition objects, keyed by bean name.
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    /**
     * List of bean definition names, in registration order.
     */
    private volatile List<String> beanDefinitionNames = new ArrayList<>(256);

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeanException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null)
            throw new BeanException("No bean named '" + beanName + "' is defined");
        return beanDefinition;
    }

    /**
     * 注册beanDefinition
     *
     * @param beanName
     * @param beanDefinition
     */
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        if (this.beanDefinitionMap.containsKey(beanName)) {
            // ignored
        } else {
            this.beanDefinitionMap.put(beanName, beanDefinition);
            this.beanDefinitionNames.add(beanName);
        }
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return this.beanDefinitionNames.contains(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionNames.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionNames.toArray(new String[0]);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        List<String> beanNames = new ArrayList<>(8);
        for (Map.Entry<String, BeanDefinition> beanDefinition : this.beanDefinitionMap.entrySet()) {
            if (beanDefinition.getValue().getBeanClass().equals(type)) {
                beanNames.add(beanDefinition.getKey());
            }
        }
        return beanNames.toArray(new String[0]);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException {
        String[] beanNamesForType = getBeanNamesForType(type);
        Map<String, T> beansOfType = new HashMap<>(beanNamesForType.length);
        for (String beanName : beanNamesForType) {
            beansOfType.put(beanName, (T) getBean(beanName));
        }
        return beansOfType;
    }

    @Override
    public String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
        List<String> beanNames = new ArrayList<>(8);
        for (Map.Entry<String, BeanDefinition> beanDefinition : this.beanDefinitionMap.entrySet()) {
            Annotation annotation = beanDefinition.getValue().getBeanClass()
                    .getAnnotation(annotationType);
            if (annotation != null) {
                beanNames.add(beanDefinition.getKey());
            }
        }
        return beanNames.toArray(new String[0]);
    }

    @Override
    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeanException {
        String[] beanNamesForAnnotation = getBeanNamesForAnnotation(annotationType);
        Map<String, Object> beansOfType = new HashMap<>(beanNamesForAnnotation.length);
        for (String beanName : beanNamesForAnnotation) {
            beansOfType.put(beanName, getBean(beanName));
        }
        return beansOfType;
    }
}
