package com.smart.lib.spring.ioc.bean.factory;

import com.smart.lib.spring.ioc.bean.exception.BeansException;
import com.smart.lib.spring.ioc.bean.factory.config.BeanDefinition;
import com.smart.lib.spring.ioc.bean.factory.config.BeanPostProcessor;
import com.smart.lib.spring.ioc.bean.factory.config.ConfigurableListableBeanFactory;
import com.smart.lib.spring.ioc.bean.factory.support.AbstractAutowireCapableBeanFactory;
import com.smart.lib.spring.ioc.bean.factory.support.BeanDefinitionRegistry;
import com.smart.lib.spring.ioc.bean.utils.Assert;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author yan
 * @version 1.0
 * @description 默认实现
 * @date 2022/8/15 10:45
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
        implements ConfigurableListableBeanFactory, BeanDefinitionRegistry {


    /**
     * BeanPostProcessors to apply in createBean.
     */
    private final List<BeanPostProcessor> beanPostProcessors = new CopyOnWriteArrayList<>();

    /**
     * Map of bean definition objects, keyed by bean name.
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    /**
     * List of bean definition names, in registration order.
     */
    private volatile List<String> beanDefinitionNames = new ArrayList<>(256);

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null)
            throw new BeansException("No bean named '" + beanName + "' is defined");
        return beanDefinition;
    }

    @Override
    protected List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {

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
    @SuppressWarnings({"rawtypes", "unchecked"})
    public String[] getBeanNamesForType(Class<?> type) {
        List<String> beanNames = new ArrayList<>(8);
        Class beanClass;
        for (Map.Entry<String, BeanDefinition> beanDefinition : this.beanDefinitionMap.entrySet()) {
            beanClass = beanDefinition.getValue().getBeanClass();
            if (beanClass.isAssignableFrom(type)
                    || Arrays.stream(beanClass.getInterfaces()).anyMatch(bean -> bean.isAssignableFrom(type))) {
                beanNames.add(beanDefinition.getKey());
            }
        }
        return beanNames.toArray(new String[0]);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
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
    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
        String[] beanNamesForAnnotation = getBeanNamesForAnnotation(annotationType);
        Map<String, Object> beansOfType = new HashMap<>(beanNamesForAnnotation.length);
        for (String beanName : beanNamesForAnnotation) {
            beansOfType.put(beanName, getBean(beanName));
        }
        return beansOfType;
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        Assert.notNull(beanPostProcessor, "BeanPostProcessor must not be null");
        // Remove from old position, if any
        this.beanPostProcessors.remove(beanPostProcessor);
        // Add to end of list
        this.beanPostProcessors.add(beanPostProcessor);
    }
}
