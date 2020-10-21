package com.customized.libs.shardingsphere.service.task.impl;

import com.customized.libs.shardingsphere.service.task.ScheduledTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author yan
 */
@Component
public class DynamicPrintTask implements ScheduledTask {

    private static final Logger log = LoggerFactory.getLogger(DynamicPrintTask.class);

    private int i;

    @Override
    public void execute() {
        log.info("thread id:{},DynamicPrintTask execute times:{}", Thread.currentThread().getId(), ++i);
    }

}