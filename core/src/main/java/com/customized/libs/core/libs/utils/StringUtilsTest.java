package com.customized.libs.core.libs.utils;

import com.alibaba.fastjson.JSON;
import com.customized.libs.core.utils.CommonUtils;
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

        System.out.println(CommonUtils.buildLogString("----", "###", 32));

        String data = "roleId&roleCode=RR00001&roleName=RR0001&status=1&roleDesc=&ApiKey=7gBZcbsC7kLIWCdELIl8nxcs&Timestamp=20201112090939&SignType=SHA256&Nonce=s9b4apmmle9&Sign=4af3c516142b602dada4e9de91fccd5e5459c50c261b7ef6357c65fd4577e02c";
        String[] rt = toKeyValue(data);
        System.out.println(JSON.toJSONString(rt, true));

        for (int i = 0; i < rt.length; i++) {
            System.out.println(rt[i]);
        }
    }

    /**
     * roleId&roleCode=RR00001&roleName=RR0001&status=1&roleDesc=&ApiKey=7gBZcbsC7kLIWCdELIl8nxcs&Timestamp=20201112090939&SignType=SHA256&Nonce=s9b4apmmle9&Sign=4af3c516142b602dada4e9de91fccd5e5459c50c261b7ef6357c65fd4577e02c:
     *
     * @param formData
     */
    public static String[] toKeyValue(String formData) {
        return formData.replace("=", ":").split("&");
    }
}
