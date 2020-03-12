package com.customized.libs.core.libs.aba;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AbaQuestionTest {

    private static AtomicInteger index = new AtomicInteger(10);

    public static void main(String[] args) {
        new Thread(() -> {
            index.compareAndSet(10, 11);
            index.compareAndSet(11, 10);
            System.out.println(Thread.currentThread().getName() + "\t: 10 -> 11 -> 10");
        }, "张三").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                boolean isSuccess = index.compareAndSet(10, 12);
                System.out.println(Thread.currentThread()
                        .getName() + "\t: Index预期的10，" + isSuccess + "，设置的新值 => " + index.get());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }, "李四").start();
    }
}
