package com.customized.libs.core.libs.thread;

import org.joda.time.DateTime;

import java.util.concurrent.TimeUnit;

/**
 * @author yan
 */
public class DaemonThreadTest {

    @SuppressWarnings("all")
    public static void main(String[] args) throws InterruptedException {
        Thread thread0 = new Thread(() -> {
            while (true) {
                System.out.println(Thread.currentThread().getId() + " ==> " + DateTime.now().toString("HH:mm:ss"));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Daemon Thread");

        thread0.setDaemon(Boolean.FALSE);
        thread0.start();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Main Thread Stoped!!");
    }
}
