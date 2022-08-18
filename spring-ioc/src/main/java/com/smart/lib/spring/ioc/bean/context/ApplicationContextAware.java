package com.smart.lib.spring.ioc.bean.context;

import com.smart.lib.spring.ioc.bean.factory.Aware;

public interface ApplicationContextAware extends Aware {

    void setApplicationContextAware(ApplicationContext applicationContext);
}
