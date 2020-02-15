package com.customized.libs.libs.blocking.queue.mode;

import com.customized.libs.libs.blocking.queue.constants.BlockingQueueConstants;
import com.customized.libs.libs.blocking.queue.item.Data;
import com.customized.libs.libs.disruptor.DisruptorThreadFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * @author yan
 */
public class DisruptorMode {


    public void run() {
        int ringBufferSize = 65536;
        DisruptorThreadFactory disruptorThreadFactory =
                new DisruptorThreadFactory("Disruptor-", true, Thread.NORM_PRIORITY) {
                    @Override
                    public Thread newThread(final Runnable r) {
                        return super.newThread(r);
                    }
                };

        // 初始化Disruptor对象
        final Disruptor<Data> disruptor = new Disruptor<>(
                Data::new,
                ringBufferSize,
                disruptorThreadFactory,
                ProducerType.MULTI,
                new YieldingWaitStrategy()
        );

        // 初始化HandleEvent
        DataConsumer consumer = new DataConsumer();

        // 绑定HandleEvent
        disruptor.handleEventsWith(consumer);

        // 启动监听
        disruptor.start();

        // 生产数据
        RingBuffer<Data> ringBuffer = disruptor.getRingBuffer();
        new Thread(() -> {
            for (long i = 0; i < BlockingQueueConstants.MAX_DATA_SIZE; i++) {
                long seq = ringBuffer.next();
                try {
                    Data data = ringBuffer.get(seq);
                    data.setId(i);
                    data.setName("c" + i);
                } finally {
                    ringBuffer.publish(seq);
                }
            }
        }).start();
    }
}
