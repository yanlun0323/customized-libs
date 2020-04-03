package com.customized.libs.core.utils.logger;

import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;

/**
 * Log4j2自定义日志格式
 *
 * @author yan
 */
@Plugin(name = "CustomizedPatternConverter", category = PatternConverter.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
@ConverterKeys({"x", "xStyle"})
public class CustomizedPatternConverter extends LogEventPatternConverter {

    private static final CustomizedPatternConverter INSTANCE = new CustomizedPatternConverter();

    public static CustomizedPatternConverter newInstance(
            final String[] options) {
        return INSTANCE;
    }

    private CustomizedPatternConverter() {
        super("xStyle", "xStyle");
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder toAppendTo) {
        toAppendTo.append(Logger.getLogId()).append("[").append(System.currentTimeMillis()).append(".").append(Logger.getLogIndex()).append("] ");
    }
}