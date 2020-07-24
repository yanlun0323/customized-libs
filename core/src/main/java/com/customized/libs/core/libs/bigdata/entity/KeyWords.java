package com.customized.libs.core.libs.bigdata.entity;

public class KeyWords implements Comparable<KeyWords> {

    private String key;

    private Integer total;


    public KeyWords() {

    }

    public KeyWords(String key, Integer total) {
        this.key = key;
        this.total = total;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


    /**
     * PriorityQueue 使用小顶堆来实现
     */
    @Override
    public int compareTo(KeyWords o) {
        return Integer.compare(this.getTotal(), o.getTotal());
    }

    @Override
    public String toString() {
        return "{key='" + key + '\'' + ", total=" + total + '}';
    }
}
