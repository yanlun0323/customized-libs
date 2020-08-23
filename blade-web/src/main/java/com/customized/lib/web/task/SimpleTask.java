package com.customized.lib.web.task;

import com.blade.ioc.annotation.Bean;
import com.blade.task.annotation.Schedule;

@Bean
public class SimpleTask {

    @Schedule(cron = "* * * * * ?")
    public void doTask() {
        System.out.println("Simple Task");
    }
}
