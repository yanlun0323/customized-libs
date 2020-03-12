package com.customized.libs.core.libs.lock;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.netflix.discovery.TimedSupervisorTask;
import org.apache.commons.lang3.RandomUtils;

import java.sql.SQLException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptibly {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(2000);

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2,
            new ThreadFactoryBuilder()
                    .setNameFormat("DiscoveryClient-%d")
                    .setDaemon(true)
                    .build());

    private static ThreadPoolExecutor heartbeatExecutor = new ThreadPoolExecutor(
            1, 100, 0, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(),
            new ThreadFactoryBuilder()
                    .setNameFormat("DiscoveryClient-HeartbeatExecutor-%d")
                    .setDaemon(true)
                    .build()
    );  // use direct handoff

    public static void main(String[] args) throws SQLException, InterruptedException {

        ReentrantLock lock = new ReentrantLock();

        for (int i = 0; i < 10; i++) {
            EXECUTOR_SERVICE.submit(() -> getConnectionInternal(lock));
        }

        discovery();

        for (int i = 0; i < 10; i++) {
            EXECUTOR_SERVICE.submit(() -> getConnectionInternal(lock));
        }
    }

    private static AtomicInteger counter = new AtomicInteger(0);

    private static void getConnectionInternal(ReentrantLock lock) {
        try {
            lock.lockInterruptibly();
            System.out.println(counter.getAndIncrement());
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    private static void discovery() {
        TimedSupervisorTask timedSupervisorTask = new TimedSupervisorTask(
                "heartbeat",
                scheduler,
                heartbeatExecutor,
                2,
                TimeUnit.SECONDS,
                20,
                new HeartbeatThread()
        );
        timedSupervisorTask.run();
    }

    /**
     * The heartbeat task that renews the lease in the given intervals.
     */
    private static class HeartbeatThread extends Thread {

        @Override
        public void run() {
            if (renew()) {

            }
        }

        private boolean renew() {
            try {
                Thread.sleep(80);
                Thread.currentThread().interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Boolean.TRUE;
        }
    }

}
