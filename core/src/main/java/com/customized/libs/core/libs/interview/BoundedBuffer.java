package com.customized.libs.core.libs.interview;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BoundedBuffer {

    private final Lock lock = new ReentrantLock();

    /**
     * 写线程锁
     */
    private final Condition notFull = lock.newCondition();

    /**
     * 读线程锁
     */
    private final Condition notEmpty = lock.newCondition();

    /**
     * 缓存队列
     */
    private final Queue<Object> cache = new PriorityQueue<>(64);

    private static final int DEFAULT_MAX_SIZE = 64;

    @SuppressWarnings("all")
    public static void main(String[] args) {
        BoundedBuffer boundedBuffer = new BoundedBuffer();
        Integer maxSize = 1000;

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < maxSize; i++) {
                try {
                    System.out.println("Thread[" + Thread.currentThread().getName() + "] Put ==> " + i);
                    boundedBuffer.put(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.setName("T1");
        t1.start();

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < maxSize; i++) {
                try {
                    System.out.println("Thread[" + Thread.currentThread().getName() + "] Take ==> " + boundedBuffer.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
        t2.setName("T2");

    }

    //写
    public void put(Object x) throws InterruptedException {
        lock.lock(); //锁定
        System.out.println("put get lock success!");
        try {

            // 如果队列满，则阻塞<写线程>
            while (cache.size() == DEFAULT_MAX_SIZE) {
                notFull.await();
            }
            // 写入队列，并更新写索引
            cache.offer(x);

            // 唤醒<读线程>
            notEmpty.signal();
        } finally {
            lock.unlock();//解除锁定
            System.out.println("put unlock");
        }
    }

    //读
    public Object take() throws InterruptedException {
        lock.lock(); //锁定
        System.out.println("take get lock success!");

        try {
            // 如果队列空，则阻塞<读线程>
            while (this.cache.size() == 0) {
                notEmpty.await();
            }

            //读取队列，并更新读索引
            Object x = cache.poll();

            // 唤醒<写线程>
            notFull.signal();
            return x;
        } finally {
            System.out.println("take unlock");
            lock.unlock();//解除锁定
        }
    }
}