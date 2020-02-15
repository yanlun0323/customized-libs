package com.customized.libs.libs.disruptor;


import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yan
 */
public class DisruptorEventPublisher {

    public static AtomicLong COUNTER = new AtomicLong(0);

    static class Translator implements EventTranslatorOneArg<LongEvent, DataItem> {

        @Override
        public void translateTo(LongEvent event, long sequence, DataItem arg0) {
            event.setData(arg0);
        }
    }

    public static Translator TRANSLATOR = new Translator();

    public static void publishEvent(Disruptor<LongEvent> disruptor, long startTime) {
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        long data = getEventData();
        ringBuffer.publishEvent(TRANSLATOR, DataItem.builder().value(data).startTime(startTime).build());
    }


    private static long getEventData() {
        return COUNTER.getAndIncrement();
    }
}
