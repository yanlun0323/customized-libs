package com.smart.lib.spring.ioc.bean.context.support;

import com.smart.lib.spring.ioc.bean.context.ApplicationContext;
import com.smart.lib.spring.ioc.bean.context.ApplicationContextAware;
import com.smart.lib.spring.ioc.bean.exception.BeansException;
import com.smart.lib.spring.ioc.bean.factory.config.BeanPostProcessor;

/**
 * 由于 ApplicationContext 的获取并不能直接在创建 Bean 时候就可以拿到，所以需要在 refresh 操作时，
 * <p>
 * 把 ApplicationContext 写入到一个包装的 BeanPostProcessor 中去，
 * <p>
 * 再由 AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsBeforeInitialization 方法调用。
 *
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/18 14:06
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContextAware(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
