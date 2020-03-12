package com.customized.libs.core.libs.date.format;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleDateFormatDemo {

    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * ThreadLocal技术方案：若有100线程同时进入，则为这100个线程备份SimpleDateFormat对象，
     * 即会调用ThreadLocal#initialValue方法100次；此为空间换时间的策略典型。
     */
    private static ThreadLocal<SimpleDateFormat> formatCache =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    public static String formatDate3(Date date) {
        SimpleDateFormat format = formatCache.get();
        return format.format(date);
    }

    public static void main(String[] args) {
        ExecutorService ts = Executors.newFixedThreadPool(100);
        for (; ; ) {
            /**
             * 这里若执行Thread.start，并不会产生线程安全问题？？<br/>
             * 用线程池的方式去多线程调用，很容易产生线程安全问题。<br/>
             * formatCache.get()会造成线程堵塞，影响性能，DATE_FORMAT则会产生线程安全问题
             */
            // new Thread(() -> {}).start();
            ts.submit(() -> {
                try {
                    String date = formatCache.get().format(new Date(Math.abs(new Random().nextLong())));
                    System.out.println(date);
                } catch (Throwable th) {
                    th.printStackTrace();
                    System.exit(1);
                }
            });
        }
    }
}
