package com.smart.lib.spring.ioc.bean.context.support;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/17 10:22
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private final String[] configLocations;


    public ClassPathXmlApplicationContext(String configLocation) {
        this(new String[]{configLocation});
    }

    public ClassPathXmlApplicationContext(String[] configLocations) {
        this.configLocations = configLocations;
        refresh();
        registerShutdownHook();
    }

    @Override
    protected String[] getConfigLocations() {
        return this.configLocations;
    }
}
