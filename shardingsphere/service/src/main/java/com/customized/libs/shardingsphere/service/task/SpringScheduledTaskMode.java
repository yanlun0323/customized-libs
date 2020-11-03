package com.customized.libs.shardingsphere.service.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author yan
 */
@Component
public class SpringScheduledTaskMode {

    private static final Logger log = LoggerFactory.getLogger(SpringScheduledTaskMode.class);

    /**
     * 最终会通过
     *
     * @see org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler#scheduleAtFixedRate(java.lang.Runnable, long)
     */
    @Scheduled(fixedRate = 3000L)
    public void innerTaskSchedule() {
        log.info("==> execute inner task scheduled by @scheduled(fixedRate)");
    }
}
