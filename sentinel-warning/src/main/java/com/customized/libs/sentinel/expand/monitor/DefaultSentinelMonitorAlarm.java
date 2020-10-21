package com.customized.libs.sentinel.expand.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yan
 */
public class DefaultSentinelMonitorAlarm implements SentinelMonitorAlarm {

    private static Logger log = LoggerFactory.getLogger(DefaultSentinelMonitorAlarm.class);

    @Override
    public void sendAlarm(String msg) {
        System.out.println(String.format("==> Send Monitor Alarm to xx. {%s}", msg));
        log.info("==> Send Monitor Alarm to xx. {}", msg);
    }
}
