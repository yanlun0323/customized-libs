package com.customized.libs.core.libs.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author yan
 */
public class StringUtilsTest {

    public static void main(String[] args) {
        List<String> methods = new ArrayList<>();
        methods.add("getUser");
        methods.add("getRoles");

        // dubbo 案例 ==> 通过HashSet来排除重复数据
        // map.put(Constants.METHODS_KEY, StringUtils.join(new HashSet<String>(Arrays.asList(methods)), ","));
        System.out.println(StringUtils.join(new HashSet<>(methods), ","));
    }
}
