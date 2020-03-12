package com.customized.libs.core.libs.hashmap;

import java.util.Map;
import java.util.TreeMap;

public class HashMapDemo {

    public static void main(String[] args) {
        Map<Student, Integer> map = new TreeMap<>();
        map.put(new Student("Michael", 99), 99);
        map.put(new Student("Bob", 88), 88);
        map.put(new Student("Alice", 77), 77);
        System.out.println(map.get(new Student("Michael", 99)));
        System.out.println(map.get(new Student("Bob", 88)));
        System.out.println(map.get(new Student("Alice", 77)));
    }
}
