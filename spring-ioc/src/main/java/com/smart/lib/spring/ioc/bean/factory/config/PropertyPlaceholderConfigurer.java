package com.smart.lib.spring.ioc.bean.factory.config;

import com.smart.lib.spring.ioc.bean.beans.PropertyValue;
import com.smart.lib.spring.ioc.bean.beans.PropertyValues;
import com.smart.lib.spring.ioc.bean.exception.BeansException;
import com.smart.lib.spring.ioc.bean.io.DefaultResourceLoader;
import com.smart.lib.spring.ioc.bean.io.Resource;
import com.smart.lib.spring.ioc.bean.utils.StringValueResolver;

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
            this.parseValuePlaceholder(beanFactory, properties);
            // 向容器添加字符串解析器，供解析@Value注解使用
            StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
            beanFactory.addEmbeddedValueResolver(valueResolver);
        } catch (Exception ex) {
            throw new BeansException("Could not load properties");
        }
    }

    private void parseValuePlaceholder(ConfigurableListableBeanFactory beanFactory
            , Properties properties) {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
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
    }

    protected String resolvePlaceholder(String placeholder, Properties props) {
        // 解析placeholder
        StringBuilder buffer = new StringBuilder(placeholder);
        int startIndex = placeholder.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        int endIndex = placeholder.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            placeholder = buffer.substring(startIndex + 2, endIndex);
        }
        return props.getProperty(placeholder);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        @Override
        public String resolveStringValue(String strVal) {
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(strVal, properties);
        }
    }
}
