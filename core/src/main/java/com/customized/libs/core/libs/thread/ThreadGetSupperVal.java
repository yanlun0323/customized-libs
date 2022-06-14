package com.customized.libs.core.libs.thread;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/5/12 10:59
 */
@SuppressWarnings({"AlibabaAvoidManuallyCreateThread", "AlibabaThreadPoolCreation"})
public class ThreadGetSupperVal {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Executor ttlExecutors = TtlExecutors.getTtlExecutor(executor);
        Thread supper = new Thread(() -> {
            ThreadLocal<String> local = new ThreadLocal<>();
            local.set("Coder.Yan");
            InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
            inheritableThreadLocal.set("Yan");
            Thread subThread = new Thread(() -> {
                System.out.println("subThread0 ==> " + local.get());
                System.out.println("subThread0 ==> " + inheritableThreadLocal.get());

                inheritableThreadLocal.set("Code.Yan");
                Thread subThread1 = new Thread(() -> {
                    System.out.println("subThread1 ==> " + local.get());
                    System.out.println("subThread1 ==> " + inheritableThreadLocal.get());
                });
                subThread1.start();
            });
            subThread.start();
        });
        supper.start();


        System.out.println("\r\n==== Thread Pool Test ====\r\n");

        InheritableThreadLocal<String> inheritableThreadLocal2 = new InheritableThreadLocal<>();
        for (int i = 0; i < 2; i++) {
            int finalI1 = i;
            new Thread(() -> {
                inheritableThreadLocal2.set("local" + finalI1);
                for (int j = 0; j < 2; j++) {
                    int finalI = finalI1;
                    int finalJ = j;
                    executor.execute(() -> {
                        executor.execute(() -> {
                            System.out.println("thead pool executor i=" + finalI + ", j=" + finalJ + " get val ==> " + inheritableThreadLocal2.get());
                        });
                    });
                }
            }).start();
        }

        System.out.println("\r\n==== Transmittable Thread Local Test ====\r\n");
        TransmittableThreadLocal<String> context = new TransmittableThreadLocal<>();
        for (int i = 0; i < 2; i++) {
            int finalI1 = i;
            new Thread(() -> {
                context.set("ttl.local" + finalI1);
                for (int j = 0; j < 2; j++) {
                    int finalI = finalI1;
                    int finalJ = j;
                    ttlExecutors.execute(() -> {
                        System.out.println("ttl.local i=" + finalI + ", j=" + finalJ + " get val ==> " + context.get());
                    });
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(15);
        executor.shutdownNow();
    }
}
