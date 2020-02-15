package com.customized.libs.libs.blocking.queue.mode;

import com.customized.libs.libs.blocking.queue.constants.BlockingQueueConstants;
import com.customized.libs.libs.blocking.queue.item.Data;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author yan
 */
public class BlockingQueueMode {

    public void run() {
        ArrayBlockingQueue<Data> queue = new ArrayBlockingQueue<>(BlockingQueueConstants.MAX_QUEUE_SIZE);

        final long startTime = System.currentTimeMillis();

        // 两个线程，一个消费，一个生产
        new Thread(() -> {
            long i = 0;
            while (i <= BlockingQueueConstants.MAX_DATA_SIZE) {
                try {
                    queue.put(new Data(i, "c" + i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }).start();

        new Thread(() -> {
            long i = 0;
            while (i <= BlockingQueueConstants.MAX_DATA_SIZE) {
                try {
                    queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("ArrayBlockingQueue costTime = " + (endTime - startTime) + "ms");
        }).start();
    }
}
