package com.customized.libs.dubbo.api.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Zale
 */
public class ExecutorsPool {

    private static final String FIXED_EXECUTOR = "fixed-executor";

    private static final String CORE_EXECUTOR = "core-executor";

    private static final String SCHEDULE_EXECUTOR = "schedule-executor";

    private ExecutorsPool() {

    }

    private static int nThreads;

    static {
        nThreads = Runtime.getRuntime().availableProcessors();
    }

    public static ExecutorService FIXED_EXECUTORS = new ThreadPoolExecutor(nThreads * 2, nThreads * 2, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(2048),
            new NamedThreadFactory(FIXED_EXECUTOR),
            new CallerRunsPolicyWithReport(FIXED_EXECUTOR)
    );

    public static ExecutorService CORE_EXECUTORS = new ThreadPoolExecutor(nThreads * 2, nThreads * 2, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1024),
            new NamedThreadFactory(CORE_EXECUTOR),
            new CallerRunsPolicyWithReport(CORE_EXECUTOR)
    );

    public static ExecutorService SCHEDULE_EXECUTORS = new ThreadPoolExecutor(nThreads * 2, nThreads * 2, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1024),
            new NamedThreadFactory(SCHEDULE_EXECUTOR),
            new CallerRunsPolicyWithReport(SCHEDULE_EXECUTOR)
    );
}