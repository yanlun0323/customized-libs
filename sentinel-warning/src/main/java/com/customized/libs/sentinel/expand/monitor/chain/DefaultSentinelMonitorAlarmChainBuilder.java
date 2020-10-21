package com.customized.libs.sentinel.expand.monitor.chain;

import com.alibaba.csp.sentinel.util.SpiLoader;
import com.customized.libs.sentinel.expand.monitor.SentinelMonitorAlarm;

import java.util.List;

/**
 * @author yan
 */
public class DefaultSentinelMonitorAlarmChainBuilder implements SentinelMonitorAlarmChainBuilder {

    @Override
    public SentinelMonitorAlarmChain build() {
        SentinelMonitorAlarmChain chain = new SentinelMonitorAlarmChain();

        // Note: the instances of ProcessorSlot should be different, since they are not stateless.
        List<SentinelMonitorAlarm> sortedAlarmList = SpiLoader.loadPrototypeInstanceListSorted(SentinelMonitorAlarm.class);
        for (SentinelMonitorAlarm alarm : sortedAlarmList) {
            chain.addLast(alarm);
        }
        return chain;
    }
}
