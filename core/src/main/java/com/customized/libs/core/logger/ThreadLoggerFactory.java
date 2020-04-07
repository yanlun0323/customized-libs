package com.customized.libs.core.logger;

import io.netty.util.concurrent.FastThreadLocal;
import org.apache.log4j.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 可针对每个线程生成单独的日志文件<br/>
 * 目前存在缺陷-会干扰正常的log4j日志
 *
 * @author yan
 */
public class ThreadLoggerFactory {

    private static FastThreadLocal<Logger> threadLocal = new FastThreadLocal<>();

    private static AtomicLong sequence = new AtomicLong(0);

    public static Logger getLogger(Class<?> clazz, Long beginSequence) {
        return getLogger(clazz.getName(), beginSequence);
    }

    public static Logger getLogger(String prefix, Long beginSequence) {
        String targetName = prefix + (beginSequence + sequence.incrementAndGet());

        Logger logger = threadLocal.get();
        if (Objects.isNull(logger)) {
            threadLocal.set(getLogger(targetName));
            logger = threadLocal.get();
        }
        return logger;
    }

    private static Logger getLogger(String logName) {
        Logger logger = Logger.getLogger(logName);
        PatternLayout layout = new PatternLayout("[%d{MM-dd HH:mm:ss}] %-5p %-8t %m%n");

        // 日志文件存放策略
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String logPath = "./logs/" + sdf.format(new Date()) + "/";

        try {
            ThreadDailyRollingFileAppender appender =
                    new ThreadDailyRollingFileAppender(layout, logPath, logName);
            appender.setAppend(false);
            appender.setImmediateFlush(true);
            appender.setThreshold(Level.DEBUG);

            logger.setLevel(Level.DEBUG);
            logger.addAppender(appender);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return logger;
    }

    static class ThreadDailyRollingFileAppender extends RollingFileAppender {

        public ThreadDailyRollingFileAppender(Layout layout, String path, String name)
                throws IOException {
            super(layout, path + name + ".log");
        }
    }
}
