package com.customized.libs.core.libs.system.clock;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("AlibabaThreadPoolCreation")
public class SystemClock {

    private static volatile long currentTimeMillis;
    private static final ScheduledExecutorService EXECUTOR_SERVICE;

    static {
        currentTimeMillis = System.currentTimeMillis();

        // 启动线程，定时刷新
        EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
        EXECUTOR_SERVICE.scheduleAtFixedRate(() -> currentTimeMillis = System.currentTimeMillis(), 1, 1, TimeUnit.MILLISECONDS);
    }

    public static void shutdownNow() {
        EXECUTOR_SERVICE.shutdownNow();
    }

    public static long currentTimeMillis() {
        return currentTimeMillis;
    }
}