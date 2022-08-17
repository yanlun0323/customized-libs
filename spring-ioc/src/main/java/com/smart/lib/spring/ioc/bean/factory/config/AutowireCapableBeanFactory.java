package com.smart.lib.spring.ioc.bean.factory.config;

import com.smart.lib.spring.ioc.bean.exception.BeansException;
import com.smart.lib.spring.ioc.bean.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * Apply {@link BeanPostProcessor BeanPostProcessors} to the given existing bean
     * instance, invoking their {@code postProcessBeforeInitialization} methods.
     * The returned bean instance may be a wrapper around the original.
     *
     * @param existingBean the existing bean instance
     * @param beanName     the name of the bean, to be passed to it if necessary
     *                     (only passed to {@link BeanPostProcessor BeanPostProcessors};
     *                     can follow the {@link #ORIGINAL_INSTANCE_SUFFIX} convention in order to
     *                     enforce the given instance to be returned, i.e. no proxies etc)
     * @return the bean instance to use, either the original or a wrapped one
     * @throws BeansException if any post-processing failed
     * @see BeanPostProcessor#postProcessBeforeInitialization
     * @see #ORIGINAL_INSTANCE_SUFFIX
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
            throws BeansException;

    /**
     * Apply {@link BeanPostProcessor BeanPostProcessors} to the given existing bean
     * instance, invoking their {@code postProcessAfterInitialization} methods.
     * The returned bean instance may be a wrapper around the original.
     *
     * @param existingBean the existing bean instance
     * @param beanName     the name of the bean, to be passed to it if necessary
     *                     (only passed to {@link BeanPostProcessor BeanPostProcessors};
     *                     can follow the {@link #ORIGINAL_INSTANCE_SUFFIX} convention in order to
     *                     enforce the given instance to be returned, i.e. no proxies etc)
     * @return the bean instance to use, either the original or a wrapped one
     * @throws BeansException if any post-processing failed
     * @see BeanPostProcessor#postProcessAfterInitialization
     * @see #ORIGINAL_INSTANCE_SUFFIX
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeansException;
}
