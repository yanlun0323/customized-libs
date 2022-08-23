package com.smart.lib.spring.ioc.bean.factory.config;

import com.smart.lib.spring.ioc.bean.beans.PropertyValue;
import com.smart.lib.spring.ioc.bean.beans.PropertyValues;
import com.smart.lib.spring.ioc.bean.context.annotation.Value;
import com.smart.lib.spring.ioc.bean.exception.BeansException;
import com.smart.lib.spring.ioc.bean.io.DefaultResourceLoader;
import com.smart.lib.spring.ioc.bean.io.Resource;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @author yan
 * @version 1.0
 * @description 处理@Value占位符（bean初始化之前处理）
 * @date 2022/8/23 14:41
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    /**
     * Default placeholder prefix: {@value}
     */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    /**
     * Default placeholder suffix: {@value}
     */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String location;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            // 配置文件加载
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(this.location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            // 字符解析
            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
            for (String beanName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                PropertyValues propertyValues = beanDefinition.getPropertyValues();

                // 获取标记@Value的属性
                Field[] declaredFields = beanDefinition.getBeanClass().getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    Value valued = declaredField.getAnnotation(Value.class);
                    if (valued != null) {
                        beanDefinition.getPropertyValues()
                                .addPropertyValue(new PropertyValue(declaredField.getName(), valued.value()));
                    }
                }
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value = propertyValue.getValue();
                    if (!(value instanceof String)) continue;
                    String strVal = (String) value;
                    StringBuilder buffer = new StringBuilder(strVal);
                    int startIndex = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
                    int endIndex = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
                    if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                        String propKey = strVal.substring(startIndex + 2, endIndex);
                        String propVal = properties.getProperty(propKey);
                        buffer.replace(startIndex, endIndex + 1, propVal);
                        propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), buffer.toString()));
                    }
                }
            }
        } catch (Exception ex) {
            throw new BeansException("Could not load properties");
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
