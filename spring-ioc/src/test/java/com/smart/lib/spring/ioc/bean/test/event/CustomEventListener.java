package com.smart.lib.spring.ioc.bean.test.event;

import com.smart.lib.spring.ioc.bean.stereotype.Component;
import com.smart.lib.spring.ioc.bean.context.ApplicationListener;

import java.util.Date;

@Component
public class CustomEventListener implements ApplicationListener<CustomEvent> {

    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("收到：" + event.getSource() + "消息;时间：" + new Date());
        System.out.println("消息：" + event.getId() + ":" + event.getMessage());
    }

}