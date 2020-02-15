package com.customized.libs.libs.local.lock;


import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class ReentrantLockTest {

    protected ReentrantLock lock;
    protected Condition notEmpty;
    protected Condition notFull;

    private List<String> data;

    private Integer max;
    private AtomicInteger counter;

    public ReentrantLockTest(Integer max) {
        this.lock = new ReentrantLock();
        this.data = new ArrayList<>();

        // Not Empty Condition监听，当收到信号，则继续消费
        // 消费成功，则释放NotFull信号，可被生产者消费
        this.notEmpty = lock.newCondition();

        // Not Full Condition监听，当收到信号，则继续创建
        // 创建成功，则释放NotEmpty信号，可被消费者消费
        this.notFull = lock.newCondition();

        this.max = max;
        this.counter = new AtomicInteger(0);
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        ReentrantLockTest lockTest = new ReentrantLockTest(10);
        // put
        executorService.submit(() -> {
            for (; ; ) {
                Thread.sleep(RandomUtils.nextLong(10, 500));
                lockTest.lock.lock();
                try {
                    while (lockTest.max == lockTest.counter.get()) {
                        lockTest.notFull.await();
                    }
                    lockTest.counter.incrementAndGet();
                    lockTest.data.add(RandomStringUtils.randomAlphabetic(10));
                    System.out.println("P ==> " + lockTest.data);

                    lockTest.notEmpty.signal();
                } finally {
                    lockTest.lock.unlock();
                }
            }
        });
        // take
        executorService.submit(() -> {
            for (; ; ) {
                Thread.sleep(RandomUtils.nextLong(10, 500));
                lockTest.lock.lock();
                try {
                    while (lockTest.counter.get() == 0) {
                        lockTest.notEmpty.await();
                    }
                    System.out.println("T " + lockTest.data);
                    lockTest.data.remove(0);
                    lockTest.counter.decrementAndGet();
                    lockTest.notFull.signal();
                } finally {
                    lockTest.lock.unlock();
                }
            }

        });
    }
}
