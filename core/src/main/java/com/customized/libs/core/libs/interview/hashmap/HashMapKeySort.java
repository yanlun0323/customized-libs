package com.customized.libs.core.libs.interview.hashmap;

import com.customized.libs.core.utils.CommonUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class HashMapKeySort {

    public static void main(String[] args) {
        sort01(buildMap());
        System.out.println(CommonUtils.buildLogString("分割线", "##", 20));
        sort02(buildMap());
        System.out.println(CommonUtils.buildLogString("分割线", "##", 20));
        sort03(buildMap());
    }

    private static Map<String, Object> buildMap() {
        Map<String, Object> data = new HashMap<>(16);
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
    public static void sort01(Map<String, Object> data) {
        data.keySet().stream().sorted().forEach(c ->
                System.out.printf("key:%s, value:%s%n", c, data.get(c))
        );
    }

    /**
     * TreeMap
     *
     * @param data
     */
    public static void sort02(Map<String, Object> data) {
        Map<String, Object> treeMap = new TreeMap<>(data);
        treeMap.forEach((key, value) -> System.out.printf("key:%s, value:%s%n", key, value));
    }

    /**
     * StreamAPI
     *
     * @param data
     */
    public static void sort03(Map<String, Object> data) {
        Map<String, Object> linkedHashMap = new LinkedHashMap<>();
        data.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(c -> linkedHashMap.put(c.getKey(), c.getValue()));
        linkedHashMap.forEach((key, value) -> System.out.printf("key:%s, value:%s%n", key, value));
    }
}
