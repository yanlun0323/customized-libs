package com.customized.libs.sentinel.expand;

import com.customized.libs.sentinel.expand.core.MonitorAlarmChainProvider;
import com.customized.libs.sentinel.expand.solt.CustomizedSoltChainBuilder;
import org.junit.Test;

public class SentinelMonitorAlramSendTest {

    @Test
    public void sendAlarm() {
        MonitorAlarmChainProvider.newMonitorChain().sendAlarm("simple alarm sender");
    }

    @Test
    public void soltChainBuild() {
        CustomizedSoltChainBuilder builder = new CustomizedSoltChainBuilder();
        builder.build();
    }
}
