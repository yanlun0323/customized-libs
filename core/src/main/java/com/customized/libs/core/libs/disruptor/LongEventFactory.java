package com.customized.libs.core.libs.disruptor;


import com.lmax.disruptor.EventFactory;

/**
 * @author yan
 */
public class LongEventFactory implements EventFactory<LongEvent> {

    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
