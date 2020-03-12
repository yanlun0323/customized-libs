package com.customized.libs.core.libs.blocking.queue.mode;

import com.customized.libs.core.libs.blocking.queue.constants.BlockingQueueConstants;
import com.customized.libs.core.libs.blocking.queue.item.Data;
import com.lmax.disruptor.EventHandler;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yan
 */
public class DataConsumer implements EventHandler<Data> {

    private long startTime;
    private AtomicLong counter;

    public DataConsumer() {
        this.startTime = System.currentTimeMillis();
        this.counter = new AtomicLong(0);
    }

    @Override
    public void onEvent(Data data, long seq, boolean bool) {
        counter.incrementAndGet();
        if (counter.get() == BlockingQueueConstants.MAX_DATA_SIZE) {
            System.out.println("Disruptor costTime = " + (System.currentTimeMillis() - startTime) + "ms");
        }
    }

}