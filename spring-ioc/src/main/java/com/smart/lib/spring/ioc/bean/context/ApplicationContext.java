package com.smart.lib.spring.ioc.bean.context;

import com.smart.lib.spring.ioc.bean.factory.HierarchicalBeanFactory;
import com.smart.lib.spring.ioc.bean.factory.ListableBeanFactory;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/17 09:22
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ApplicationEventPublisher {
}
