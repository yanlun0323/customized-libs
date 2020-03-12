package com.customized.libs.core.libs.disruptor;


import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yan
 */
public class DisruptorRunner {

    private static ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(50, 156,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());

    private static long backgroundThreadId; // LOG4J2-471

    public static void main(String[] args) {
        DisruptorThreadFactory disruptorThreadFactory = new DisruptorThreadFactory("Disruptor-", true, Thread.NORM_PRIORITY) {
            @Override
            public Thread newThread(final Runnable r) {
                final Thread result = super.newThread(r);
                backgroundThreadId = result.getId();
                return result;
            }
        };
        EventFactory<LongEvent> eventEventFactory = new LongEventFactory();

        //RingBuffer大小必须是2的N次方
        int ringBufferSize = 1024 * 1024;

        Disruptor<LongEvent> disruptor = new Disruptor<>(eventEventFactory, ringBufferSize, disruptorThreadFactory,
                ProducerType.SINGLE, new YieldingWaitStrategy());

        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();

        // 发布事件
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        long sequence = ringBuffer.next();

        long start = System.currentTimeMillis();
        try {
            LongEvent event = ringBuffer.get(sequence);
            long data = getEventData();
            event.setData(DataItem.builder().value(data).startTime(start).build());
        } finally {
            ringBuffer.publish(sequence);
        }

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            long finalStart = start;
            EXECUTOR_SERVICE.submit(() -> DisruptorEventPublisher.publishEvent(disruptor, finalStart));
        }
        System.out.println(System.currentTimeMillis() - start);

        disruptor.shutdown();
    }

    private static long getEventData() {
        return RandomUtils.nextLong();
    }
}
