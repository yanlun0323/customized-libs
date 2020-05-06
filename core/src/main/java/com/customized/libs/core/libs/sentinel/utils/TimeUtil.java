package com.customized.libs.core.libs.sentinel.utils;

import java.util.concurrent.TimeUnit;

/**
 * Provides millisecond-level time of OS.
 *
 * @author yan
 */
public class TimeUtil {

    /**
     * 使用volatile关键字的场景<br/>
     * synchronized关键字是防止多个线程同时执行一段代码，那么就会很影响程序执行效率，而volatile关键字在某些情况下性能要优于synchronized，但是要注意volatile关键字是无法替代synchronized关键字的，因为volatile关键字无法保证操作的原子性。通常来说，使用volatile必须具备以下2个条件：
     * <p>
     * 　　1）对变量的写操作不依赖于当前值
     * <p>
     * 　　2）该变量没有包含在具有其他变量的不变式中
     * <p>
     * 　　实际上，这些条件表明，可以被写入 volatile 变量的这些有效值独立于任何程序的状态，包括变量的当前状态。
     * <p>
     * 　　事实上，我的理解就是上面的2个条件需要保证操作是原子性操作，才能保证使用volatile关键字的程序在并发时能够正确执行。
     */
    private static volatile long currentTimeMillis;

    // 静态代码块中执行数据初始化及动态更新
    static {
        currentTimeMillis = System.currentTimeMillis();

        Thread daemon = new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {

                }

                // 保证第一次获取数据的准确性
                currentTimeMillis = System.currentTimeMillis();
            }
        });

        // 不能把正在运行的线程设置为守护线程（所以setDaemon必须在start之前调用）

        // 用户线程和守护线程的区别：
        //
        // 1. 主线程结束后用户线程还会继续运行,JVM存活；主线程结束后守护线程和JVM的状态又下面第2条确定。
        //
        // 2.如果没有用户线程，都是守护线程，那么JVM结束（随之而来的是所有的一切烟消云散，包括所有的守护线程）。
        daemon.setDaemon(true);
        daemon.setName("sentinel-time-tick-thread");
        daemon.start();
    }

    public static long currentTimeMillis() {
        return currentTimeMillis;
    }
}
