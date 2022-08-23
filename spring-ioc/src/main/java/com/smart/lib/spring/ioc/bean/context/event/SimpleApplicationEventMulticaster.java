package com.smart.lib.spring.ioc.bean.context.event;

import com.smart.lib.spring.ioc.bean.context.ApplicationEvent;
import com.smart.lib.spring.ioc.bean.context.ApplicationListener;

import java.util.Collection;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/23 13:48
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {


    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void multicastEvent(ApplicationEvent event) {
        Collection<ApplicationListener> applicationListeners = getApplicationListeners(event);
        for (ApplicationListener applicationListener : applicationListeners) {
            applicationListener.onApplicationEvent(event);
        }
    }
}
