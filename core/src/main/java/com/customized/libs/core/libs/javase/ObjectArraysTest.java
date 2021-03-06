package com.customized.libs.core.libs.javase;

import com.alibaba.fastjson.JSON;

public class ObjectArraysTest {

    public static void main(String[] args) {
        Object strs = new String[]{"aa", "bb"};
        Object[] obj = (Object[]) strs;
        display((Object[]) strs, "a");
        display(obj);
    }

    public static void display(Object... args) {
        System.out.println(JSON.toJSONString(args));
        String title = "删除用户%s，登录时间%s";
        String format = String.format(title, args);

        System.out.println(format);
    }
}
