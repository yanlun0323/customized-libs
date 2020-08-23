package com.customized.libs.core.libs.javase.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch 类位于 java.util.concurrent 包下，利用它可以实现类似计数器的功能。比如有
 * 一个任务 A，它要等待其他 4 个任务执行完毕之后才能执行，此时就可以利用 CountDownLatch
 * 来实现这种功能了。
 * <p>
 * 线程计数器
 *
 * @author yan
 */
public class CountDownLatchDemo {

    @SuppressWarnings("all")
    public static void main(String[] args) throws InterruptedException {
        /*
         * 定义两个任务值
         */
        CountDownLatch countDownLatch = new CountDownLatch(2);


        // 定义两个线程
        Thread task0 = new Thread(() -> {
            System.out.println("==> Task0 Starting..");
            try {
                TimeUnit.SECONDS.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("<== Task0 End..");
            countDownLatch.countDown();
        });
        task0.start();

        Thread task1 = new Thread(() -> {
            System.out.println("==> Task1 Starting..");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("<== Task1 End..");
            countDownLatch.countDown();
        });
        task1.start();

        TimeUnit.SECONDS.sleep(5);

        System.out.println("Wait for the task to complete！!");

        countDownLatch.await();

        System.out.println("All task Completed!!");
    }
}
