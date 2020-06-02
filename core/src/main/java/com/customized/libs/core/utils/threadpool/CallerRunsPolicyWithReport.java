package com.customized.libs.core.utils.threadpool;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yan
 */
public class CallerRunsPolicyWithReport extends ThreadPoolExecutor.CallerRunsPolicy {

    protected static final Logger logger = LoggerFactory.getLogger(CallerRunsPolicyWithReport.class);

    private final String threadName;

    public CallerRunsPolicyWithReport(String threadName) {
        this.threadName = threadName;
    }

    /**
     * Executes task r in the caller's thread, unless the executor
     * has been shut down, in which case the task is discarded.
     *
     * @param r the runnable task requested to be executed
     * @param e the executor attempting to execute this task
     */
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        String msg = String.format("Thread pool is EXHAUSTED!" +
                        " Thread Name: %s, Pool Size: %d (active: %d, core: %d, max: %d, largest: %d), Task: %d (completed: %d)," +
                        " Executor status:(isShutdown:%s, isTerminated:%s, isTerminating:%s)!",
                threadName, e.getPoolSize(), e.getActiveCount(), e.getCorePoolSize(), e.getMaximumPoolSize(), e.getLargestPoolSize(),
                e.getTaskCount(), e.getCompletedTaskCount(), e.isShutdown(), e.isTerminated(), e.isTerminating());
        logger.warn(msg);

        if (!e.isShutdown()) {
            r.run();
        }
    }

}