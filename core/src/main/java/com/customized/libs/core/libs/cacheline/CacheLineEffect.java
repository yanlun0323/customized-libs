package com.customized.libs.core.libs.cacheline;

/**
 * @author gongming
 * @description
 * @date 16/6/4
 */
public class CacheLineEffect {
    /**
     * int数组
     * Loop times:11ms
     * Loop times:28ms
     */
    //考虑一般缓存行大小是64字节，一个 long 类型占8字节
    static long[][] arr;

    /**
     * long数组
     * Loop times:18ms
     * Loop times:47ms
     */

    public static void main(String[] args) {
        arr = new long[1024 * 1024][];
        for (int i = 0; i < 1024 * 1024; i++) {
            arr[i] = new long[8];
            for (int j = 0; j < 8; j++) {
                arr[i][j] = 0L;
            }
        }
        long sum = 0L;
        long marked = System.currentTimeMillis();
        for (int i = 0; i < 1024 * 1024; i += 1) {
            for (int j = 0; j < 8; j++) {
                sum = arr[i][j];
            }
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");

        marked = System.currentTimeMillis();
        for (int i = 0; i < 8; i += 1) {
            for (int j = 0; j < 1024 * 1024; j++) {
                sum = arr[j][i];
            }
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");


        int[] arr = new int[64 * 1024 * 1024];

        marked = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] *= 3;
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");

        marked = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i += 16) {
            arr[i] *= 3;
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");

        marked = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i += 32) {
            arr[i] *= 3;
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");

        marked = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i += 17) {
            arr[i] *= 3;
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");


        System.out.println("********************************");


        int steps = 256 * 1024 * 1024;
        int[] a = new int[2];

        marked = System.currentTimeMillis();
        // Loop 1
        for (int i = 0; i < steps; i++) {
            a[0]++;
            a[0]++;
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");

        marked = System.currentTimeMillis();
        // Loop 2
        for (int i = 0; i < steps; i++) {
            a[0]++;
            a[1]++;
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");
    }
}