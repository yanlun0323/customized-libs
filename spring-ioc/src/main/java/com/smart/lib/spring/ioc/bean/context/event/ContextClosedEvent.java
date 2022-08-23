package com.smart.lib.spring.ioc.bean.context.event;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/8/23 10:10
 */
public class ContextClosedEvent extends ApplicationContextEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
