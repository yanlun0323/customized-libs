package com.customized.libs.provider.config;

import com.alipay.common.tracer.core.appender.TracerLogRootDaemon;
import com.alipay.common.tracer.core.appender.self.SelfLog;
import com.alipay.common.tracer.core.appender.self.TracerDaemon;
import com.alipay.common.tracer.core.utils.StringUtils;
import com.alipay.common.tracer.core.utils.TracerUtils;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import static com.alipay.common.tracer.core.appender.TracerLogRootDaemon.TRACER_APPEND_PID_TO_LOG_PATH_KEY;

/**
 * @author yan
 */
@Deprecated
public class SofaTracerPropertiesAutoConfig implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        try {
            // this.putSystemProperty("sofa.tracer.properties");

            // this.reloadTracerLogRootDaemon();
        } catch (Exception e) {
            SelfLog.info("sofa.tracer.properties文件不存在");
        }
    }

    private void putSystemProperty(String propertiesFileName) throws Exception {
        InputStream inStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(propertiesFileName);

        if (inStream != null) {
            Properties prop = new Properties();
            prop.load(inStream);

            for (Object okey : prop.keySet()) {
                String key = (String) okey;
                System.setProperty(key, prop.getProperty(key));
            }
            SelfLog.info("Load system property finished: [" + propertiesFileName + "] ");
        } else {
            SelfLog.warn("Load system property fail, Can't find file: [" + propertiesFileName + "]");
        }
    }

    private void reloadTracerLogRootDaemon() {
        String loggingRoot = System.getProperty("loggingRoot");
        if (StringUtils.isBlank(loggingRoot)) {
            loggingRoot = System.getProperty("logging.path");
        }

        String appendPidToLogPathString = System.getProperty(TRACER_APPEND_PID_TO_LOG_PATH_KEY);
        boolean appendPidToLogPath = "true".equalsIgnoreCase(appendPidToLogPathString);

        if (StringUtils.isBlank(loggingRoot)) {
            loggingRoot = System.getProperty("user.home") + File.separator + "logs";
        }

        String tempLogFileDir = loggingRoot + File.separator + "tracelog";

        if (appendPidToLogPath) {
            tempLogFileDir = tempLogFileDir + File.separator + TracerUtils.getPID();
        }

        TracerLogRootDaemon.LOG_FILE_DIR = tempLogFileDir;

        try {
            TracerDaemon.start();
        } catch (Throwable e) {
            SelfLog.error("Failed to start Tracer Daemon Thread", e);
        }
    }
}
