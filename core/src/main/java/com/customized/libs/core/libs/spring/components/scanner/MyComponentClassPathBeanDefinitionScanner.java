package com.customized.libs.core.libs.spring.components.scanner;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * 1、extends ClassPathBeanDefinitionScanner
 * <p>
 * 2、registerFilters
 * <p>
 * 3、doScan方法覆写
 *
 * @author yan
 */
public class MyComponentClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public MyComponentClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    protected void registerDefaultFilters() {
        super.addIncludeFilter(new AnnotationTypeFilter(MyComponent.class));
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        return super.doScan(basePackages);
    }
}
