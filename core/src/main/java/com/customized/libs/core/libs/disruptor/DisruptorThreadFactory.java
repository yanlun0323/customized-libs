package com.customized.libs.core.libs.disruptor;


import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yan
 */
public class DisruptorThreadFactory implements ThreadFactory {

    private static final String PREFIX = "TF-";

    /**
     * Creates a new daemon thread factory.
     *
     * @param threadFactoryName The thread factory name.
     * @return a new daemon thread factory.
     */
    public static DisruptorThreadFactory createDaemonThreadFactory(final String threadFactoryName) {
        return new DisruptorThreadFactory(threadFactoryName, true, Thread.NORM_PRIORITY);
    }

    /**
     * Creates a new thread factory.
     * <p>
     * This is mainly used for tests. Production code should be very careful with creating
     * non-daemon threads since those will block application shutdown
     * (see https://issues.apache.org/jira/browse/LOG4J2-1748).
     *
     * @param threadFactoryName The thread factory name.
     * @return a new daemon thread factory.
     */
    public static DisruptorThreadFactory createThreadFactory(final String threadFactoryName) {
        return new DisruptorThreadFactory(threadFactoryName, false, Thread.NORM_PRIORITY);
    }

    private static final AtomicInteger FACTORY_NUMBER = new AtomicInteger(1);
    private static final AtomicInteger THREAD_NUMBER = new AtomicInteger(1);
    private final boolean daemon;
    private final ThreadGroup group;
    private final int priority;
    private final String threadNamePrefix;


    /**
     * Constructs an initialized thread factory.
     *
     * @param threadFactoryName The thread factory name.
     * @param daemon            Whether to create daemon threads.
     * @param priority          The thread priority.
     */
    public DisruptorThreadFactory(final String threadFactoryName, final boolean daemon, final int priority) {
        this.threadNamePrefix = PREFIX + FACTORY_NUMBER.getAndIncrement() + "-" + threadFactoryName + "-";
        this.daemon = daemon;
        this.priority = priority;
        final SecurityManager securityManager = System.getSecurityManager();
        this.group = securityManager != null ? securityManager.getThreadGroup()
                : Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable runnable) {
        final Thread thread = new DisruptorThread(group,
                runnable, threadNamePrefix + THREAD_NUMBER.getAndIncrement(), 0);
        if (thread.isDaemon() != daemon) {
            thread.setDaemon(daemon);
        }
        if (thread.getPriority() != priority) {
            thread.setPriority(priority);
        }
        return thread;
    }
}
