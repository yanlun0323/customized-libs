package com.customized.libs.core.libs.javase.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer在执行定时任务时只会创建一个线程，如果存在多个任务，若其中某个任务因为某种原因而导致任务执行时间过长，超过了两个任务的间隔时间，会发生一些缺陷
 */
public class TimerTest01 {


    private Timer timer;
    public long start;

    public TimerTest01() {
        this.timer = new Timer();
        start = System.currentTimeMillis();
    }

    public void timerOne() {
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("timerOne invoked ,the time:" + (System.currentTimeMillis() - start));
                try {
                    Thread.sleep(4000); // 线程休眠4000
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }

    public void timerTwo() {
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("timerTwo invoked ,the time:" + (System.currentTimeMillis() - start));
            }
        }, 3000);
    }

    public static void main(String[] args) throws Exception {
        TimerTest01 test = new TimerTest01();

        test.timerOne();
        test.timerTwo();
    }
}