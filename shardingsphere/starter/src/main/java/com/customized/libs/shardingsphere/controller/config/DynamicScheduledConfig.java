package com.customized.libs.shardingsphere.controller.config;

import com.customized.libs.shardingsphere.entity.generate.TSpringScheduledCron;
import com.customized.libs.shardingsphere.service.TaskConfigService;
import com.customized.libs.shardingsphere.service.task.ScheduledTask;
import com.customized.libs.shardingsphere.service.utils.threadpool.CallerRunsPolicyWithReport;
import com.customized.libs.shardingsphere.service.utils.threadpool.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author yan
 */
@Configuration
public class DynamicScheduledConfig implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(DynamicScheduledConfig.class);

    @Autowired
    private TaskConfigService taskConfigService;

    @Autowired
    private ApplicationContext context;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        List<TSpringScheduledCron> records = this.taskConfigService.findAll();

        // 任务配置检测
        for (TSpringScheduledCron record : records) {
            Class<?> clazz;
            Object task;
            try {
                clazz = Class.forName(record.getCronKey());
                task = context.getBean(clazz);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("spring_scheduled_cron表数据" + record.getCronKey() + "有误", e);
            } catch (BeansException e) {
                throw new IllegalArgumentException(record.getCronKey() + "未纳入到spring管理", e);
            }
            Assert.isAssignable(ScheduledTask.class, task.getClass(), "定时任务类必须实现ScheduledOfTask接口");

            // 可以通过改变数据库数据进而实现动态改变执行周期
            taskRegistrar.addTriggerTask(((Runnable) task),
                    triggerContext -> {
                        String cronExpression = taskConfigService.findByCronKey(record.getCronKey()).getCronExpression();
                        log.info("==> refresh task({}) cron expression({})", record.getCronKey(), cronExpression);
                        return new CronTrigger(cronExpression).nextExecutionTime(triggerContext);
                    }
            );
        }
    }

    @Bean
    public Executor taskExecutor() {
        return new ScheduledThreadPoolExecutor(8, new NamedThreadFactory("schedule-task"),
                new CallerRunsPolicyWithReport("schedule-task-report")
        );
    }
}
