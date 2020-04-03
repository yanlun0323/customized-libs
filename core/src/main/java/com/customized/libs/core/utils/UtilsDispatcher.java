package com.customized.libs.core.utils;

import com.customized.libs.core.config.SDKConst;
import com.customized.libs.core.config.WebConfiguration;
import com.customized.libs.core.utils.BeanUtils;

public class UtilsDispatcher {

    public static void main(String[] args) {
        System.out.println(BeanUtils.buildDefaultBeanName(WebConfiguration.class.getName()));
        System.out.println(BeanUtils.buildDefaultBeanName(SDKConst.class.getName()));
    }
}
