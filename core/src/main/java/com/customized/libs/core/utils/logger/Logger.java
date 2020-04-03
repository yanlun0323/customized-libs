package com.customized.libs.core.utils.logger;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author JueYue.Qu
 * @version 1.1去掉getLogId 统一由Layout处理,修复打印多次[]的问题
 */
public class Logger {

    public static final String LOG_ID_INDEX = "logIdIndex";
    public static final String LOG_ID = "logId";

    public static ThreadLocal<String> threadLocalLogId = new ThreadLocal<String>();
    public static ThreadLocal<AtomicLong> threadLocalIndex = new ThreadLocal<AtomicLong>();

    private final org.apache.log4j.Logger logger;

    final static String FQCN = Logger.class.getName();

    private Logger(Class<?> clazz) {
        logger = org.apache.log4j.Logger.getLogger(clazz);
    }

    private Logger() {
        logger = org.apache.log4j.Logger.getRootLogger();

    }

    public org.apache.log4j.Logger getInnerLogger() {
        return logger;
    }

    public static Logger getLogger() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        return new Logger(sts[2].getClass());
    }

    public static Logger getLogger(Class<?> clazz) {
        return new Logger(clazz);
    }

    public static Logger getRootLogger() {
        return new Logger();
    }

    public static String getLogId() {
        String logId = threadLocalLogId.get();
        if (null == logId || "".equals(logId)) {
            logId = RpcContext.getContext().getAttachment(LOG_ID);
        }
        if (null == logId || "".equals(logId)) {
            logId = UUID.randomUUID().toString();
        }
        threadLocalLogId.set(logId);
        if (logId.startsWith("[")) {
            return logId;
        }
        return "[" + logId + "] ";
    }

    public static long getLogIndex() {
        AtomicLong index = threadLocalIndex.get();
        if (null == index) {
            if (StringUtils.isNotEmpty(RpcContext.getContext().getAttachment(LOG_ID_INDEX))) {
                index = new AtomicLong(Long.parseLong(RpcContext.getContext().getAttachment(LOG_ID_INDEX)));
            }
        }
        if (null == index) {
            index = new AtomicLong();
        }
        threadLocalIndex.set(index);
        return index.incrementAndGet();
    }

    public void log(String callerFQCN, Level level, String msg, Throwable t) {

        logger.log(callerFQCN, level, msg, t);
    }

    /**
     * debug日志输出
     *
     * @param message
     */
    public void trace(Object message) {
        if (logger.isTraceEnabled()) {
            if (message instanceof java.lang.String) {
                logger.trace(message);
            } else {
                logger.trace(JSON.toJSONString(message));
            }
        }
    }

    /**
     * 带异常的日志输出
     *
     * @param message
     * @param t
     */
    public void trace(Object message, Throwable t) {
        if (logger.isTraceEnabled()) {
            if (message instanceof java.lang.String) {
                logger.trace(message, t);
            } else {
                logger.trace(JSON.toJSONString(message), t);
            }
        }
    }

    /**
     * debug日志输出
     *
     * @param message
     */
    public void debug(Object message) {
        if (logger.isDebugEnabled()) {
            if (message instanceof java.lang.String) {
                logger.debug(message);
            } else {
                logger.debug(JSON.toJSONString(message));
            }
        }
    }

    /**
     * 带异常的日志输出
     *
     * @param message
     * @param t
     */
    public void debug(Object message, Throwable t) {
        if (logger.isDebugEnabled()) {
            if (message instanceof java.lang.String) {
                logger.debug(message, t);
            } else {
                logger.debug(JSON.toJSONString(message), t);
            }
        }
    }

    /**
     * 对日志带格式化输出
     *
     * @param format
     * @param args
     */
    public void debugFormat(String format, Object... args) {
        String msg = String.format(format, args);
        debug(msg);
    }

    /**
     * 带异常的日志格式化输出
     *
     * @param t
     * @param format
     * @param args
     */

    public void debugFormat(Throwable t, String format, Object... args) {
        String msg = String.format(format, args);
        debug(msg, t);
    }

    /**
     * info日志输出
     *
     * @param message
     */
    public void info(Object message) {
        if (logger.isInfoEnabled()) {
            if (message instanceof java.lang.String) {
                logger.info(message);
            } else {
                logger.info(JSON.toJSONString(message));
            }
        }
    }

    /**
     * 带异常的info日志输出
     *
     * @param message
     * @param t
     */
    public void info(Object message, Throwable t) {
        if (logger.isInfoEnabled()) {
            if (message instanceof java.lang.String) {
                logger.info(message, t);
            } else {
                logger.info(JSON.toJSONString(message), t);
            }
        }
    }

    /**
     * 对info日志带格式化输出
     *
     * @param format
     * @param args
     */
    public void infoFormat(String format, Object... args) {
        String msg = String.format(format, args);
        info(msg);
    }

    /**
     * 带异常的info日志格式化输出
     *
     * @param t
     * @param format
     * @param args
     */

    public void infoFormat(Throwable t, String format, Object... args) {
        String msg = String.format(format, args);
        info(msg, t);
    }

    /**
     * warn日志输出
     *
     * @param message
     */
    public void warn(Object message) {
        if (message instanceof java.lang.String) {
            logger.warn(message);
        } else {
            logger.warn(JSON.toJSONString(message));
        }
    }

    /**
     * 带异常的warn日志输出
     *
     * @param message
     * @param t
     */
    public void warn(Object message, Throwable t) {
        if (message instanceof java.lang.String) {
            logger.warn(message, t);
        } else {
            logger.warn(JSON.toJSONString(message), t);
        }
    }

    /**
     * 对warn日志带格式化输出
     *
     * @param format
     * @param args
     */
    public void warnFormat(String format, Object... args) {
        String msg = String.format(format, args);
        warn(msg);
    }

    /**
     * 带异常的warn日志格式化输出
     *
     * @param t
     * @param format
     * @param args
     */

    public void warnFormat(Throwable t, String format, Object... args) {
        String msg = String.format(format, args);
        warn(msg, t);
    }

    /**
     * error日志输出
     *
     * @param message
     */
    public void error(Object message) {
        if (message instanceof java.lang.String) {
            logger.error(message);
        } else {
            logger.error(JSON.toJSONString(message));
        }
    }

    /**
     * 带异常的error日志输出
     *
     * @param message
     * @param t
     */
    public void error(Object message, Throwable t) {
        if (message instanceof java.lang.String) {
            logger.error(message, t);
        } else {
            logger.error(JSON.toJSONString(message), t);
        }
    }

    /**
     * 对error日志带格式化输出
     *
     * @param format
     * @param args
     */
    public void errorFormat(String format, Object... args) {
        String msg = String.format(format, args);
        error(msg);
    }

    /**
     * 带异常的error日志格式化输出
     *
     * @param t
     * @param format
     * @param args
     */

    public void errorFormat(Throwable t, String format, Object... args) {
        String msg = String.format(format, args);
        error(msg, t);
    }

    /**
     * fatal日志输出
     *
     * @param message
     */
    public void fatal(Object message) {
        if (message instanceof java.lang.String) {
            logger.fatal(message);
        } else {
            logger.fatal(JSON.toJSONString(message));
        }
    }

    /**
     * 带异常的fatal日志输出
     *
     * @param message
     * @param t
     */
    public void fatal(Object message, Throwable t) {
        if (message instanceof java.lang.String) {
            logger.fatal(message, t);
        } else {
            logger.fatal(JSON.toJSONString(message), t);
        }
    }

    /**
     * 对fatal日志带格式化输出
     *
     * @param format
     * @param args
     */
    public void fatalFormat(String format, Object... args) {
        String msg = String.format(format, args);
        fatal(msg);
    }

    /**
     * 带异常的fatal日志格式化输出
     *
     * @param t
     * @param format
     * @param args
     */

    public void fatalFormat(Throwable t, String format, Object... args) {
        String msg = String.format(format, args);
        fatal(msg, t);
    }

    /**
     * debug级别日志是否可用
     *
     * @return
     */
    public Boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    /**
     * Info级别日志是否可用
     *
     * @return
     */
    public Boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

}
