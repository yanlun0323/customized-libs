package com.customized.libs.core.mvc.factory;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

/**
 * @author yan
 */
public class CustomizedClassPathMapperScanner extends ClassPathBeanDefinitionScanner {

    public CustomizedClassPathMapperScanner(BeanDefinitionRegistry registry
            , Class<? extends Annotation> annotationType) {
        super(registry, false);
        addIncludeFilter(new AnnotationTypeFilter(annotationType));
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        if (beanDefinitionHolders.isEmpty()) {
            logger.warn("No IRequest mapper was found in '" + Arrays.toString(basePackages)
                    + "' package. Please check your configuration.");
        }
        return beanDefinitionHolders;
    }
}
