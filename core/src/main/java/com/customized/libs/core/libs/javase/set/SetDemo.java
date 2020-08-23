package com.customized.libs.core.libs.javase.set;

import com.alibaba.fastjson.JSON;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 这是SetJava中可用的标准实现的顺序特征的简要概述：
 * <p>
 * 保持插入顺序：LinkedHashSet和CopyOnWriteArraySet（线程安全）
 * <p>
 * 将项目保持在集合内排序：TreeSet，EnumSet（特定于枚举）和ConcurrentSkipListSet（线程安全）
 * <p>
 * 不会以任何特定顺序保留项目：HashSet（您尝试过的项目）
 * <p>
 * 对于您的特定情况，您可以先对项目进行排序，然后使用1或2（最有可能是LinkedHashSet或TreeSet）中的任何一个。或者，或者更有效地，您可以将未排序的数据添加到中，TreeSet该数据将自动为您进行排序。
 */
public class SetDemo {

    public static void main(String[] args) {

        setWithCopyOnWriteArraySet();

        setWithLinkedHashSet();

        setWithTrree();

        setWithHash();
    }

    /**
     * 线程安全的实现
     */
    private static void setWithCopyOnWriteArraySet() {
        Set<String> data = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 10; i++) {
            data.add(String.valueOf(i));
        }

        Set<String> data0 = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 10; i++) {
            data0.add(String.valueOf(i) + "_");
        }

        data.addAll(data0);
        System.out.println("CopyOnWriteArraySet <> " + JSON.toJSONString(data.toArray(new String[0])));
    }

    /**
     * 可保证顺序
     */
    private static void setWithLinkedHashSet() {
        Set<String> data = new LinkedHashSet<>();
        for (int i = 0; i < 10; i++) {
            data.add(String.valueOf(i));
        }

        Set<String> data0 = new LinkedHashSet<>();
        for (int i = 0; i < 10; i++) {
            data0.add(String.valueOf(i) + "_");
        }

        data.addAll(data0);
        System.out.println("LinkedHashSet <> " + JSON.toJSONString(data.toArray(new String[0])));
    }


    private static void setWithTrree() {
        Set<String> data = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            data.add(String.valueOf(i));
        }

        Set<String> data0 = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            data0.add(String.valueOf(i) + "_");
        }

        data.addAll(data0);
        System.out.println("TreeSet<> " + JSON.toJSONString(data.toArray(new String[0])));
    }


    private static void setWithHash() {
        Set<String> data = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            data.add(String.valueOf(i));
        }

        Set<String> data0 = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            data0.add(String.valueOf(i) + "_");
        }

        data.addAll(data0);
        System.out.println("HashSet<> " + JSON.toJSONString(data.toArray(new String[0])));
    }
}
