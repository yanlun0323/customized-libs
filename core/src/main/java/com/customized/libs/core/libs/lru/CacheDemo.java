package com.customized.libs.core.libs.lru;

import com.alibaba.fastjson.JSON;
import com.customized.libs.core.utils.CommonUtils;
import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.Weighers;

/**
 * 利用JDK-LinkedHashMap实现LRU算法
 *
 * @author yan
 */
public class CacheDemo {

    public static void main(String[] args) {
        LRUCache<Integer, Integer> cache = new LRUCache<>(5);
        for (int i = 0; i < 16; i++) {
            cache.put(i, i);
            cache.get(4);
            System.out.println(cache);
        }

        System.out.println(CommonUtils.buildLogString("############", "*", 32));

        LRUCache2<Integer, Integer> cache2 = new LRUCache2<>(5);
        for (int i = 0; i < 16; i++) {
            cache2.put(i, i);
            cache2.get(4);
            System.out.println(cache2.getCache());
        }


        System.out.println(CommonUtils.buildLogString("############", "*", 32));

        ConcurrentLinkedHashMap<Integer, Integer> cache3 = new ConcurrentLinkedHashMap.Builder<Integer, Integer>()
                .maximumWeightedCapacity(5).weigher(Weighers.singleton()).build();
        for (int i = 0; i < 16; i++) {
            cache3.put(i, i);
            cache3.get(4);

            System.out.println(cache3);
        }
    }
}
