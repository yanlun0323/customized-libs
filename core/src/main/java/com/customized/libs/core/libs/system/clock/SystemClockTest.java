package com.customized.libs.core.libs.system.clock;

import com.customized.libs.core.libs.sentinel.utils.TimeUtil;
import org.springframework.util.StopWatch;

/**
 * @author yan
 * @version 1.0
 * @description 优化system.currentTimeMillis卡顿
 * @date 2022/3/7 13:39
 */
public class SystemClockTest {

    public static void main(String[] args) throws InterruptedException {
        int defaultSize = 50000000;
        System.out.println(Integer.MAX_VALUE);
        StopWatch stopWatch0 = new StopWatch("getCurrentTimeMillis");
        stopWatch0.start("SystemClock.currentTimeMillis()");
        for (int i = 0; i < defaultSize; i++) {
            SystemClock.currentTimeMillis();
        }
        stopWatch0.stop();
        System.out.println("SystemClock.currentTimeMillis() Total time ==> " + stopWatch0.getLastTaskTimeMillis() + "ms");


        stopWatch0.start("System.currentTimeMillis()");
        for (int i = 0; i < defaultSize; i++) {
            System.currentTimeMillis();
        }
        stopWatch0.stop();
        System.out.println("System.currentTimeMillis() Total time ==> " + stopWatch0.getLastTaskTimeMillis() + "ms");


        stopWatch0.start("TimeUtil.currentTimeMillis()");
        for (int i = 0; i < defaultSize; i++) {
            TimeUtil.currentTimeMillis();
        }
        stopWatch0.stop();
        System.out.println("TimeUtil.currentTimeMillis() Total time ==> " + stopWatch0.getLastTaskTimeMillis() + "ms");

        System.out.println(stopWatch0.prettyPrint());

        // TimeUtil.shutdownNow();
        SystemClock.shutdownNow();
    }
}
