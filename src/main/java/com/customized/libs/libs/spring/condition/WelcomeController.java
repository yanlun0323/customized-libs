package com.customized.libs.libs.spring.condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

/**
 * @author yan
 */
@Component
@ConditionalOnBean(AmazonTest.class)
public class WelcomeController {

    private static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);

    private AppProperties app;
    private GlobalProperties global;

    @Autowired
    public void setApp(AppProperties app) {
        this.app = app;
    }

    @Autowired
    public void setGlobal(GlobalProperties global) {
        this.global = global;
    }

    public void testWelcome() {
        String appProperties = app.toString();
        String globalProperties = global.toString();
        System.out.println(appProperties);
        System.out.println(globalProperties);
    }
}