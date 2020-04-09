package com.customized.libs.core.libs.spring.components.scanner;

import com.customized.libs.core.libs.spring.components.scanner.expand.MyBeanProxyFactory;
import com.customized.libs.core.utils.BeanUtils;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Arrays;
import java.util.Objects;
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
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);

        if (beanDefinitionHolders.isEmpty()) {
            logger.warn("No Service was found in '" + Arrays.toString(basePackages)
                    + "' package. Please check your configuration.");
        }

        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitionHolders) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();

            // 为每一个bean动态生成一个代理类实现，直接覆盖原始的BeanDefinition定义
            GenericBeanDefinition beanDefinitionProxy = new GenericBeanDefinition();
            beanDefinitionProxy.getPropertyValues().add("beanProxy", Objects.requireNonNull(getRegistry())
                    .getBeanDefinition("beanProxyHandler"));
            beanDefinitionProxy.getPropertyValues().add("targetInterface", definition.getBeanClassName());
            beanDefinitionProxy.getPropertyValues().add("target", definition);
            beanDefinitionProxy.setBeanClass(MyBeanProxyFactory.class);

            String targetBeanName = BeanUtils.buildDefaultBeanName(definition.getBeanClassName());
            getRegistry().registerBeanDefinition(targetBeanName, beanDefinitionProxy);
        }

        return beanDefinitionHolders;
    }
}
