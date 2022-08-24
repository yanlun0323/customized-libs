package com.smart.lib.spring.ioc.bean.context.annotation;

import com.smart.lib.spring.ioc.bean.stereotype.Component;
import com.smart.lib.spring.ioc.bean.factory.config.BeanDefinition;
import com.smart.lib.spring.ioc.bean.utils.ClassUtils;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/23 15:10
 */
public class ClassPathScanningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtils.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> aClass : classes) {
            candidates.add(new BeanDefinition(aClass));
        }
        return candidates;
    }
}
