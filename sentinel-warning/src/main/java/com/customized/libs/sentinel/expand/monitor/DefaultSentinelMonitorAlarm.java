package com.customized.libs.sentinel.expand.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yan
 */
public class DefaultSentinelMonitorAlarm implements SentinelMonitorAlarm {

    private final static Logger log = LoggerFactory.getLogger(DefaultSentinelMonitorAlarm.class);

    @Override
    public void sendAlarm(String msg) {
        System.out.printf("==> Send Monitor Alarm to xx. {%s}%n", msg);
        log.info("==> Send Monitor Alarm to xx. {}", msg);
    }
}
