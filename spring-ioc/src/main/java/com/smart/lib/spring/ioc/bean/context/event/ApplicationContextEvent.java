package com.smart.lib.spring.ioc.bean.context.event;

import com.smart.lib.spring.ioc.bean.context.ApplicationContext;
import com.smart.lib.spring.ioc.bean.context.ApplicationEvent;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/23 10:09
 */
public class ApplicationContextEvent extends ApplicationEvent {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}