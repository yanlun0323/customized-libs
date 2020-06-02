package com.customized.libs.core.utils;

import com.customized.libs.core.utils.threadpool.NamedThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Zale
 */
public class ExecutorsPool {

    private ExecutorsPool() {

    }

    private static int nThreads;

    static {
        nThreads = Runtime.getRuntime().availableProcessors();
    }

    public static ExecutorService FIXED_EXECUTORS = new ThreadPoolExecutor(nThreads * 2, nThreads * 2, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(2048),
            new NamedThreadFactory("fixed-executor"),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public static ExecutorService TRANS_EXECUTORS = new ThreadPoolExecutor(nThreads * 2, nThreads * 2, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1024),
            new NamedThreadFactory("trans-executor"),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public static ExecutorService SCHEDULE_EXECUTORS = new ThreadPoolExecutor(nThreads * 2, nThreads * 2, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1024),
            new NamedThreadFactory("schedule-executor"),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );
}