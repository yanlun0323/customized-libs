package com.customized.libs.sentinel.expand;

import com.customized.libs.sentinel.expand.core.MonitorAlarmChainProvider;
import org.junit.Test;

public class SentinelMonitorAlramSendTest {

    @Test
    public void sendAlarm() {
        MonitorAlarmChainProvider.newMonitorChain().sendAlarm("simple alarm sender");
    }
}
