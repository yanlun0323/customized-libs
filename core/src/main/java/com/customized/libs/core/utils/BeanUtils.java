package com.customized.libs.core.utils;

import org.springframework.util.ClassUtils;

import java.beans.Introspector;

public class BeanUtils {

    public static String buildDefaultBeanName(String beanClassName) {
        String shortClassName = ClassUtils.getShortName(beanClassName);
        return Introspector.decapitalize(shortClassName);
    }
}
