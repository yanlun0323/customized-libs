package com.customized.libs.core.libs.lru;

/**
 * 利用JDK-LinkedHashMap实现LRU算法
 */
public class CacheDemo {

    public static void main(String[] args) {
        LRUCache<Integer, Integer> cache = new LRUCache<>(2);
        for (int i = 0; i < 16; i++) {
            cache.put(i, i);
            System.out.println(cache);
        }
    }
}
