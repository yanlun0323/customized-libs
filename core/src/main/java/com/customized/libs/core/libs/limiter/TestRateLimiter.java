package com.customized.libs.core.libs.limiter;

import com.google.common.util.concurrent.RateLimiter;
import org.apache.commons.lang3.RandomUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yan
 */
public class TestRateLimiter implements Runnable {

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 允许每秒最多10个任务
     */
    private static RateLimiter rateLimiter = RateLimiter.create(10);

    @SuppressWarnings("all")
    public static void main(String[] arg) throws InterruptedException {
        for (int i = 0; i < 200; i++) {
            /*
             * 请求令牌,超过许可会被阻塞，此当时与Sentinel类似，但是无法保证后续的出口流量准确被限流
             */
            rateLimiter.acquire();
            Thread.sleep(RandomUtils.nextInt(10, 200));
            Thread t = new Thread(new TestRateLimiter());
            t.start();
        }
    }

    @Override
    public void run() {
        System.out.println(sdf.format(new Date()) + " Task End..");
    }
}