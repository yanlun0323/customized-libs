package com.smart.lib.spring.ioc.bean.test.event;

import com.smart.lib.spring.ioc.bean.annotations.Component;
import com.smart.lib.spring.ioc.bean.context.ApplicationListener;
import com.smart.lib.spring.ioc.bean.context.event.ContextClosedEvent;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/23 14:10
 */
@Component
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("容器已关闭。。。。线程销毁");
        System.out.println(event.getSource());
    }
}
