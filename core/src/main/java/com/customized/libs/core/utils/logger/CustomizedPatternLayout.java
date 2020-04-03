package com.customized.libs.core.utils.logger;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 日志输出模版自定义
 */
public class CustomizedPatternLayout extends PatternLayout {

    @Override
    public String format(LoggingEvent event) {
        return Logger.getLogId() + "[" + System.currentTimeMillis() + "." + Logger.getLogIndex() + "] " + super.format(event);
    }
}
