package com.customized.libs.core.libs.javase.timer.replaced;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Timer在执行定时任务时只会创建一个线程，如果存在多个任务，若其中某个任务因为某种原因而导致任务执行时间过长，超过了两个任务的间隔时间，会发生一些缺陷
 *
 * @author yan
 */
@Slf4j
public class TimerTest01Replaced {


    private ScheduledExecutorService timer;
    public long start;

    /**
     * TODO 这里若是corePoolSize设置为1，则两个线程抢占资源，导致运行异常
     */
    public TimerTest01Replaced() {
        //org.apache.commons.lang3.concurrent.BasicThreadFactory
        this.timer = new ScheduledThreadPoolExecutor(8,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
        start = System.currentTimeMillis();
    }

    /**
     * 这里使用scheduleWithFixedDelay或scheduleAtFixedRate是有区别的
     * <p>
     * FixedDelay和FixedRate，前者会等待任务完成后执行，即每个任务会间隔5秒，后者4秒以固定频率执行
     */
    public void timerOne() {
        timer.scheduleWithFixedDelay(() -> {
            log.warn("timerOne invoked ,the time:" + (System.currentTimeMillis() - start));
            try {
                Thread.sleep(4000); // 线程休眠4000
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public void timerTwo() {
        timer.scheduleAtFixedRate(() -> {
            log.warn("timerTwo invoked ,the time:" + (System.currentTimeMillis() - start));
        }, 1, 3, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws IOException {
        TimerTest01Replaced test = new TimerTest01Replaced();

        test.timerOne();
        test.timerTwo();

        System.in.read();
    }
}