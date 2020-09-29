package com.customized.libs.core.libs.spring.util;

import org.apache.commons.lang.time.StopWatch;

import java.util.concurrent.TimeUnit;

public class StopWatchTest {

    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch0 = new StopWatch();
        stopWatch0.start();

        TimeUnit.SECONDS.sleep(1);

        stopWatch0.stop();

        System.out.println(stopWatch0.toString());

        org.springframework.util.StopWatch stopWatch = new org.springframework.util.StopWatch("customized:libs");
        stopWatch.start("task0");
        TimeUnit.SECONDS.sleep(1);
        stopWatch.stop();

        stopWatch.start("task1");
        TimeUnit.SECONDS.sleep(2);
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
    }
}
