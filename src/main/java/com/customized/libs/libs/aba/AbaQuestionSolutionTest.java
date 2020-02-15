package com.customized.libs.libs.aba;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AbaQuestionSolutionTest {

    private static AtomicInteger index = new AtomicInteger(10);

    private static AtomicStampedReference<Integer> stampedRef = new AtomicStampedReference<>(10, 1);

    public static void main(String[] args) {
        new Thread(() -> {
            int stamp = stampedRef.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 第1次版本号：" + stamp);

            stampedRef.compareAndSet(10, 11,
                    stampedRef.getStamp(), stampedRef.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t 第2次版本号：" + stampedRef.getStamp());

            stampedRef.compareAndSet(11, 10,
                    stampedRef.getStamp(), stampedRef.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t 第3次版本号：" + stampedRef.getStamp());
        }, "张三").start();

        new Thread(() -> {
            try {
                int stamp = stampedRef.getStamp();
                System.out.println(Thread.currentThread().getName() + "\t 第1次版本号：" + stamp);

                TimeUnit.SECONDS.sleep(2);
                boolean isSuccess = stampedRef.compareAndSet(10, 12,
                        stampedRef.getStamp(), stampedRef.getStamp() + 1);
                System.out.println(Thread.currentThread().getName() + "\t 修改是否成功："
                        + isSuccess + "，当前版本：" + stampedRef.getStamp());

                System.out.println(Thread.currentThread().getName() + "当前实际值：" + stampedRef.getReference());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }, "李四").start();
    }
}
