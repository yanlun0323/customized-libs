package com.customized.libs.core.libs.spring.env;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author yan
 */
@Component
public class RefreshPropertySourceScheduler {

    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    private RefreshPropertySource refreshPropertySource;

    @PostConstruct
    public void setup() {
        environment.getPropertySources().addLast(refreshPropertySource);
    }

    /**
     * 将自定义的RefreshPropertySource添加到Spring的环境中定时刷新
     */
    @Scheduled(cron = "*/5 * * * * ?")
    public void scheduling() {
        refreshPropertySource.refresh();
        environment.getPropertySources().addLast(refreshPropertySource);
        System.out.println(environment.getProperty("env"));
    }
}
