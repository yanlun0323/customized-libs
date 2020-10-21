package com.customized.libs.sentinel.expand.monitor.chain;

/**
 * @author yan
 */
public interface SentinelMonitorAlarmChainBuilder {

    /**
     * 构建预警发送链服务
     *
     * @return
     */
    SentinelMonitorAlarmChain build();
}
