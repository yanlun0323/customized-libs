package com.proxy.smart.core.utils;

import com.proxy.smart.core.utils.threadpool.CallerRunsPolicyWithReport;
import com.proxy.smart.core.utils.threadpool.NamedThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorsPool {

    private static final String FIXED_EXECUTOR = "fixed-executor";
    private static final String CORE0_EXECUTOR = "core0-executor";
    private static final String CORE1_EXECUTOR = "core1-executor";

    private static int nThreads;

    static {
        nThreads = Runtime.getRuntime().availableProcessors();
    }

    public static ExecutorService FIXED_EXECUTORS = new ThreadPoolExecutor(64 * 2, 64 * 2, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(2048),
            new NamedThreadFactory(FIXED_EXECUTOR),
            new CallerRunsPolicyWithReport(FIXED_EXECUTOR)
    );

    public static ExecutorService CORE0_EXECUTORS = new ThreadPoolExecutor(64 * 2, 64 * 2, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(2048),
            new NamedThreadFactory(CORE0_EXECUTOR),
            new CallerRunsPolicyWithReport(CORE0_EXECUTOR)
    );

    public static ExecutorService CORE1_EXECUTORS = new ThreadPoolExecutor(nThreads * 2, nThreads * 2, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(2048),
            new NamedThreadFactory(CORE1_EXECUTOR),
            new CallerRunsPolicyWithReport(CORE1_EXECUTOR)
    );
}
