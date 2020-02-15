package com.customized.libs.libs.utils;

import com.customized.libs.config.SDKConst;
import com.customized.libs.config.WebConfiguration;

public class UtilsDispatcher {

    public static void main(String[] args) {
        System.out.println(BeanUtils.buildDefaultBeanName(WebConfiguration.class.getName()));
        System.out.println(BeanUtils.buildDefaultBeanName(SDKConst.class.getName()));
    }
}
