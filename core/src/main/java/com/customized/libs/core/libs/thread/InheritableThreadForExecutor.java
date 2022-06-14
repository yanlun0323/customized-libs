package com.customized.libs.core.libs.thread;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public class InheritableThreadForExecutor {

    static final InheritableThreadLocal<String> ITL = new InheritableThreadLocal<>();
    static final InheritableThreadLocal<String> TTL = new TransmittableThreadLocal<>();
    static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(1);
    static final Executor TTL_EXECUTOR = TtlExecutors.getTtlExecutor(EXECUTOR);

    public static void main(String[] args) throws Exception {
        ITL.set("throwable");
        TTL.set("throwable");
        TTL_EXECUTOR.execute(() -> {
            System.out.println(ITL.get());
            System.out.println(TTL.get());
        });
        ITL.set("doge");
        TTL.set("doge");
        TTL_EXECUTOR.execute(() -> {
            System.out.println(ITL.get());
            System.out.println(TTL.get());
        });
        TimeUnit.SECONDS.sleep(5);
        EXECUTOR.shutdownNow();
    }
}