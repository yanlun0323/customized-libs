package com.smart.lib.spring.ioc.bean.context.event;

import com.smart.lib.spring.ioc.bean.context.ApplicationEvent;
import com.smart.lib.spring.ioc.bean.context.ApplicationListener;

public interface ApplicationEventMulticaster {

    void addApplicationListener(ApplicationListener<?> listener);

    void removeApplicationListener(ApplicationListener<?> listener);

    void multicastEvent(ApplicationEvent event);
}
