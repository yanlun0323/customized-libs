package com.customized.libs.shardingsphere.service.task;


import com.customized.libs.shardingsphere.entity.common.StatusEnum;
import com.customized.libs.shardingsphere.entity.generate.TSpringScheduledCron;
import com.customized.libs.shardingsphere.service.TaskConfigService;
import com.customized.libs.shardingsphere.service.core.SpringUtil;

/**
 * @author yan
 */
public interface ScheduledTask extends Runnable {

    /**
     * 定时任务方法
     */
    void execute();

    /**
     * 实现控制定时任务启用或禁用的功能
     */
    @Override
    default void run() {
        TaskConfigService repository = SpringUtil.getObject(TaskConfigService.class);
        TSpringScheduledCron scheduledCron = repository.findByCronKey(this.getClass().getName());

        if (StatusEnum.DISABLE.getCode()
                .equals(scheduledCron.getStatus().intValue())) {
            return;
        }
        execute();
    }
}