package com.smart.lib.spring.ioc.bean.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.smart.lib.spring.ioc.bean.annotations.Component;
import com.smart.lib.spring.ioc.bean.factory.config.BeanDefinition;
import com.smart.lib.spring.ioc.bean.factory.support.BeanDefinitionRegistry;

import java.util.Set;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/23 15:13
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    private final BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidateComponents = this.findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : candidateComponents) {
                // 解析bean作用域
                String beanScope = resolveBeanScope(beanDefinition);
                if (StrUtil.isNotBlank(beanScope)) {
                    beanDefinition.setScope(beanScope);
                }
                // 注册
                registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }
    }

    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        // beanDefinition.getBeanClass().getAnnotation(Scope.class)报错，可强制转换
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (scope != null) {
            return scope.value();
        }
        return StrUtil.EMPTY;
    }

    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }
}
