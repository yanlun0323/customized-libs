package com.customized.libs.core.libs.limiter;

import com.customized.libs.core.utils.ExecutorsPool;

/**
 * @author yan
 */
public class LeapArrayTest {

    public static void main(String[] args) throws InterruptedException {

        LeapArray leapArray = new LeapArray(1000, 20);


        for (int j = 0; j < 10; j++) {
            Thread.sleep(100);

            for (int i = 0; i < 50; i++) {
                ExecutorsPool.FIXED_EXECUTORS.submit(() -> {
                    WindowWrap wrap = leapArray.currentWindow(TimeUtil.currentTimeMillis());
                    wrap.add();
                    if (wrap.getValue() > 20) {
                        System.err.println("Reject... Current metric ==> " + wrap.getValue());
                    } else {
                        System.out.println("Pass... Current metric ==> " + wrap.getValue());
                    }
                });
            }
        }
    }
}
