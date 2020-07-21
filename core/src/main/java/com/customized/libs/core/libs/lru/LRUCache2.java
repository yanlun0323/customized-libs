package com.customized.libs.core.libs.lru;

import java.util.LinkedHashMap;

/**
 * 利用LinkedHashMap有多线程安全问题
 *
 * @author yan
 */
public class LRUCache2<K, V> {

    private LinkedHashMap<K, CacheNode<K, V>> cache;

    private int capacity;

    public LinkedHashMap<K, CacheNode<K, V>> getCache() {
        return cache;
    }

    public LRUCache2(int capacity) {
        this.cache = new LinkedHashMap<>(capacity);
        this.capacity = capacity;
    }

    /**
     * 成功访问后将数据插值末尾
     *
     * @param key
     * @return
     */
    public V get(K key) {
        if (!cache.containsKey(key)) return null;

        CacheNode<K, V> node = cache.get(key);
        V val = node.getVal();

        // 移位至末尾
        cache.remove(key);
        cache.put(key, node);
        return val;
    }

    /**
     * put前删除，然后判断是否已满，若已满则删除表头
     *
     * @param key
     * @param val
     */
    public void put(K key, V val) {
        if (cache.containsKey(key)) {
            cache.remove(key);
        }

        // cache已经满了
        if (capacity == cache.size()) {
            cache.remove(cache.keySet().iterator().next());
        }
        cache.put(key, new CacheNode<>(key, val));
    }

    public class CacheNode<K, V> {

        private K key;
        private V val;

        public CacheNode() {

        }

        public CacheNode(K key, V val) {
            this.key = key;
            this.val = val;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getVal() {
            return val;
        }

        public void setVal(V val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "CacheNode{" +
                    "key=" + key +
                    ", val=" + val +
                    '}';
        }
    }
}
