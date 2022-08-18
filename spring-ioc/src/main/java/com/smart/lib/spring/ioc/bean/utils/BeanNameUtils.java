package com.smart.lib.spring.ioc.bean.utils;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/18 14:27
 */
public class BeanNameUtils {

    public static String translateBeanName(Class<?> beanClass) {
        String simpleName = beanClass.getSimpleName();
        char[] cs = simpleName.toCharArray();
        cs[0] += 32;
        return String.valueOf(cs);
    }
}
