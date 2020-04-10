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

        // 在覆写doScan这一步RegisterBeanDefinition，同名的bean注册，会直接覆盖原有的beanDefinition。
        // 那么就能在spring bean容器中获取到代理后的bean对象。
        // 这里我拿到的实际上是BeanDefinitionHolder对象，实际上是一份beanDefinitionHolder的拷贝，这也是为什么add target beanDefinition后能正确拿到实现类对象的原因。
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
            // getRegistry() == org.springframework.beans.factory.support.DefaultListableBeanFactory
            // beanDefinition存储在org.springframework.beans.factory.support.DefaultListableBeanFactory.beanDefinitionMap对象内
            getRegistry().registerBeanDefinition(targetBeanName, beanDefinitionProxy);
        }

        return beanDefinitionHolders;
    }
}
