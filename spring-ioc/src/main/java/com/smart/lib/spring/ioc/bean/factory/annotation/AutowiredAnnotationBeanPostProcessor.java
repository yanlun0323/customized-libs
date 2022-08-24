package com.smart.lib.spring.ioc.bean.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import com.smart.lib.spring.ioc.bean.beans.PropertyValues;
import com.smart.lib.spring.ioc.bean.exception.BeansException;
import com.smart.lib.spring.ioc.bean.factory.BeanFactory;
import com.smart.lib.spring.ioc.bean.factory.BeanFactoryAware;
import com.smart.lib.spring.ioc.bean.factory.config.ConfigurableListableBeanFactory;
import com.smart.lib.spring.ioc.bean.factory.config.InstantiationAwareBeanPostProcessor;
import com.smart.lib.spring.ioc.bean.utils.ClassUtils;

import java.lang.reflect.Field;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/24 14:28
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    public AutowiredAnnotationBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        // @Value注解
        Class<?> clazz = bean.getClass();
        clazz = ClassUtils.isCglibProxy(clazz) ? clazz.getSuperclass() : clazz;
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Value annotation = declaredField.getAnnotation(Value.class);
            if (annotation != null) {
                String val = annotation.value();
                val = beanFactory.resolveEmbeddedValue(val);
                BeanUtil.setFieldValue(bean, declaredField.getName(), val);
            }
        }
        // 2. 处理注解 @Autowired
        for (Field field : declaredFields) {
            Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
            if (null != autowiredAnnotation) {
                Class<?> fieldType = field.getType();
                String dependentBeanName = null;
                Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
                Object dependentBean = null;
                if (null != qualifierAnnotation) {
                    dependentBeanName = qualifierAnnotation.value();
                    dependentBean = beanFactory.getBean(dependentBeanName, fieldType);
                } else {
                    dependentBean = beanFactory.getBean(fieldType);
                }
                BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
            }
        }
        return pvs;
    }
}
