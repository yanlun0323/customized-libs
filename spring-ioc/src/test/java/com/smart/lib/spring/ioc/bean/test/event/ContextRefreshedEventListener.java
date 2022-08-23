package com.smart.lib.spring.ioc.bean.test.event;

import com.alibaba.fastjson.JSON;
import com.smart.lib.spring.ioc.bean.context.ApplicationListener;
import com.smart.lib.spring.ioc.bean.context.event.ContextRefreshedEvent;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/23 14:10
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println(JSON.toJSONString(event.getApplicationContext().getBeanDefinitionNames(), true));
        System.out.println("容器初始化完成");
    }
}
