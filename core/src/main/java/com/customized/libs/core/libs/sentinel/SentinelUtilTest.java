package com.customized.libs.core.libs.sentinel;

import com.customized.libs.core.libs.sentinel.utils.TimeUtil;

import java.util.concurrent.TimeUnit;

public class SentinelUtilTest {

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 20; i++) {
            TimeUnit.MILLISECONDS.sleep(1000);
            System.out.println(TimeUtil.currentTimeMillis());
        }
    }
}

