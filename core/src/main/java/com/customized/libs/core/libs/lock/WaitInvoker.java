package com.customized.libs.core.libs.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author yan
 */
@Service
@Slf4j
public class WaitInvoker {

    public void invoke(String key) throws InterruptedException {
        long begin = System.currentTimeMillis();
        // this.waitTime(60000L);
        Thread.sleep(6000L);
        log.warn(">>> [Thread ==> {}] Wait Invoker ==> {} ==> {}ms", Thread.currentThread().getId(),
                key, System.currentTimeMillis() - begin);
    }

    private void waitTime(long waitTime) {
        long future = System.currentTimeMillis() + waitTime;
        long remaining = waitTime;
        try {
            synchronized (this) {
                while (remaining > 0) {
                    this.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
