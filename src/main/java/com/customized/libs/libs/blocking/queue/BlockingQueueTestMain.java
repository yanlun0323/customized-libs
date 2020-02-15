package com.customized.libs.libs.blocking.queue;

import com.customized.libs.libs.blocking.queue.mode.BlockingQueueMode;
import com.customized.libs.libs.blocking.queue.mode.DisruptorMode;

/**
 * @author yan
 */
public class BlockingQueueTestMain {

    public static void main(String[] args) {
        BlockingQueueMode mode0 = new BlockingQueueMode();

        mode0.run();

        DisruptorMode mode1 = new DisruptorMode();

        mode1.run();
    }
}
