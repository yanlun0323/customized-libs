package com.customized.libs.core.libs.interview.hashmap;

import com.customized.libs.core.utils.CommonUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HashMapValueSort {

    public static void main(String[] args) {
        sort01(buildMap());
        System.out.println(CommonUtils.buildLogString("分割线", "##", 20));
    }

    private static Map<String, String> buildMap() {
        Map<String, String> data = new HashMap<>(16);
        data.put("username", "java");
        data.put("type", "00");
        data.put("password", "012345");
        data.put("createBy", "ADMIN");
        return data;
    }

    /**
     * keySet排序
     *
     * @param data
     */
    public static <T extends Comparable<? super T>> void sort01(Map<String, T> data) {
        Map<String, Object> linkedHashMap = new LinkedHashMap<>();
        data.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                .forEachOrdered(c -> linkedHashMap.put(c.getKey(), c.getValue()));
        linkedHashMap.forEach((key, value) -> System.out.printf("key:%s, value:%s%n", key, value));
    }
}
