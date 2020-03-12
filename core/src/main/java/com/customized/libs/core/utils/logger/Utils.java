package com.customized.libs.core.utils.logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 多线程LoggerUtils<br/>
 * 可针对每个线程生成单独的日志文件
 *
 * @author yan
 */
public class Utils {

    private static Long beginSequence = System.currentTimeMillis() + 220000000000L;

    public static void info(String message) {
        Logger log = ThreadLoggerFactory.getLogger(StringUtils.EMPTY, beginSequence);
        log.info(message);
    }

    public static void warn(String message) {
        Logger log = ThreadLoggerFactory.getLogger(StringUtils.EMPTY, beginSequence);
        log.warn(message);
    }
}
