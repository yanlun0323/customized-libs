package com.customized.libs.core.libs.javase.timer.replaced;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 如果TimerTask抛出RuntimeException，Timer会终止所有任务的运行。如下
 */
@Slf4j
public class TimerTest02Replaced {

    private ScheduledThreadPoolExecutor timer;

    public TimerTest02Replaced() {
        //org.apache.commons.lang3.concurrent.BasicThreadFactory
        this.timer = new ScheduledThreadPoolExecutor(8,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
    }

    public void timerOne() {
        timer.scheduleAtFixedRate(() -> {
            try {
                throw new RuntimeException();
            } catch (Exception ex) {
                log.error("Run Exception", ex);
                throw ex;
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void timerTwo() {
        timer.scheduleAtFixedRate(() -> {
            System.out.println("我会不会执行呢？？");

        }, 0, 1, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws IOException {
        TimerTest02Replaced test = new TimerTest02Replaced();
        test.timerOne();
        test.timerTwo();

        System.in.read();
    }
}