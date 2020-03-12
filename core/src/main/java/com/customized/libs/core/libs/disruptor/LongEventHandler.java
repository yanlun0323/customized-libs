package com.customized.libs.core.libs.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * @author yan
 */
public class LongEventHandler implements EventHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
        System.out.println("Sequence ==> " + sequence + "\tEvent ==> "
                + event + "\t TimeConsuming ==> " + (System.currentTimeMillis() - event.getData().getStartTime()));
    }
}
