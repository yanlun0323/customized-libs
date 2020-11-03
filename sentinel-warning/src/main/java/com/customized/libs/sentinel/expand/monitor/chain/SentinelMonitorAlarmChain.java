package com.customized.libs.sentinel.expand.monitor.chain;

import com.customized.libs.sentinel.expand.monitor.SentinelMonitorAlarm;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author yan
 */
public class SentinelMonitorAlarmChain {

    private final List<SentinelMonitorAlarm> container = new CopyOnWriteArrayList<>();

    public void addLast(SentinelMonitorAlarm sender) {
        this.container.add(sender);
    }

    public void addFirst(SentinelMonitorAlarm sender) {
        this.container.add(0, sender);
    }

    public void sendAlarm(String msg) {
        container.forEach(c -> c.sendAlarm(msg));
    }
}
