package com.customized.libs.core.libs.javase.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 如果TimerTask抛出RuntimeException，Timer会终止所有任务的运行。如下
 */
public class TimerTest02 {
    private Timer timer;

    public TimerTest02() {
        this.timer = new Timer();
    }

    public void timerOne() {
        timer.schedule(new TimerTask() {
            public void run() {
                throw new RuntimeException();
            }
        }, 1000);
    }

    public void timerTwo() {
        timer.schedule(new TimerTask() {

            public void run() {
                System.out.println("我会不会执行呢？？");
            }
        }, 1000);
    }

    public static void main(String[] args) {
        TimerTest02 test = new TimerTest02();
        test.timerOne();
        test.timerTwo();
    }
}