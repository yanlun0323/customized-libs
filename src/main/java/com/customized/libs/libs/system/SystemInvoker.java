package com.customized.libs.libs.system;

import com.alibaba.fastjson.JSON;

import java.util.Map;
import java.util.Properties;

/**
 * @author yan
 */
public class SystemInvoker {

    public static void main(String[] args) {

        // 系统环境变量
        Map<String, String> getenv = System.getenv();

        System.out.println(JSON.toJSONString(getenv));

        // JAVA内置属性变量
        Properties properties = System.getProperties();

        System.out.println(JSON.toJSONString(properties));
    }
}
