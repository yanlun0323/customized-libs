package com.customized.libs.core.logger.layout.log4j;

import com.customized.libs.core.logger.CustomizedLogger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 日志输出模版自定义
 *
 * @author yan
 */
public class CustomizedPatternLayout extends PatternLayout {

    @Override
    public String format(LoggingEvent event) {
        return CustomizedLogger.getDefaultFormatter() + super.format(event);
    }
}
