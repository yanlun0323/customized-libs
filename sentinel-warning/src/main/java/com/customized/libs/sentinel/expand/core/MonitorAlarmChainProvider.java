/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.customized.libs.sentinel.expand.core;

import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.util.SpiLoader;
import com.customized.libs.sentinel.expand.monitor.chain.DefaultSentinelMonitorAlarmChainBuilder;
import com.customized.libs.sentinel.expand.monitor.chain.SentinelMonitorAlarmChain;
import com.customized.libs.sentinel.expand.monitor.chain.SentinelMonitorAlarmChainBuilder;

/**
 * A provider for creating slot chains via resolved slot chain builder SPI.
 *
 * @author Eric Zhao
 * @since 0.2.0
 */
public final class MonitorAlarmChainProvider {

    private static volatile SentinelMonitorAlarmChainBuilder chainBuilder = null;

    /**
     * The load and pick process is not thread-safe, but it's okay since the method should be only invoked
     * via {@code lookProcessChain} in {@link com.alibaba.csp.sentinel.CtSph} under lock.
     *
     * @return new created slot chain
     */
    public static SentinelMonitorAlarmChain newMonitorChain() {
        if (chainBuilder != null) {
            return chainBuilder.build();
        }

        // Resolve the slot chain builder SPI.
        chainBuilder = SpiLoader.loadFirstInstanceOrDefault(SentinelMonitorAlarmChainBuilder.class
                , DefaultSentinelMonitorAlarmChainBuilder.class);

        if (chainBuilder == null) {
            // Should not go through here.
            RecordLog.warn("[MonitorAlarmChainProvider] Wrong state when resolving slot chain builder, using default");
            chainBuilder = new DefaultSentinelMonitorAlarmChainBuilder();
        } else {
            RecordLog.info("[MonitorAlarmChainProvider] Global slot chain builder resolved: "
                    + chainBuilder.getClass().getCanonicalName());
        }
        return chainBuilder.build();
    }

    private MonitorAlarmChainProvider() {
    }
}
