package com.smart.lib.spring.ioc.bean.context;

import java.util.EventObject;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/23 10:08
 */
public abstract class ApplicationEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
