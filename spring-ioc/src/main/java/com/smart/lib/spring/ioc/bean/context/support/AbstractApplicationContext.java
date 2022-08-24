package com.smart.lib.spring.ioc.bean.context.support;

import com.smart.lib.spring.ioc.bean.context.ApplicationEvent;
import com.smart.lib.spring.ioc.bean.context.ApplicationListener;
import com.smart.lib.spring.ioc.bean.context.ConfigurableApplicationContext;
import com.smart.lib.spring.ioc.bean.context.event.ApplicationEventMulticaster;
import com.smart.lib.spring.ioc.bean.context.event.ContextClosedEvent;
import com.smart.lib.spring.ioc.bean.context.event.ContextRefreshedEvent;
import com.smart.lib.spring.ioc.bean.context.event.SimpleApplicationEventMulticaster;
import com.smart.lib.spring.ioc.bean.exception.BeansException;
import com.smart.lib.spring.ioc.bean.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.smart.lib.spring.ioc.bean.factory.config.BeanFactoryPostProcessor;
import com.smart.lib.spring.ioc.bean.factory.config.BeanPostProcessor;
import com.smart.lib.spring.ioc.bean.factory.config.ConfigurableListableBeanFactory;
import com.smart.lib.spring.ioc.bean.io.DefaultResourceLoader;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * bean注册首先要读取资源（默认资源加载器）
 *
 * @author yan
 * @version 1.0
 * @description 抽象服务
 * @date 2022/8/17 09:29
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    private Thread shutdownHook;

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    /**
     * Synchronization monitor for the "refresh" and "destroy".
     */
    private final Object startupShutdownMonitor = new Object();

    @Override
    public void refresh() throws BeansException {
        // 1、创建beanFactory，并加载BeanDefinition
        refreshBeanFactory();
        // 2、获取beanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        prepareBeanFactory(beanFactory);

        // 3. 添加 ApplicationContextAwareProcessor，让继承自 ApplicationContextAware 的 Bean 对象都能感知所属的 ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor(beanFactory));

        // 4. 在 Bean 实例化之前，执行 BeanFactoryPostProcessor (Invoke factory processors registered as beans in the context.)
        invokeBeanFactoryPostProcessors(beanFactory);
        // 5. BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);
        // 6. 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();
        // 初始化事件发布者
        initApplicationEventMulticaster();
        // 注册事件监听器
        registerListeners();
        // 发布容齐刷新完成事件
        finishRefresh();
    }

    private void initApplicationEventMulticaster() {
        // 注册到bean容齐
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster();
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    @SuppressWarnings("rawtypes")
    private void registerListeners() {
        // 获取所有事件bean
        Map<String, ApplicationListener> beansOfType = getBeansOfType(ApplicationListener.class);
        for (ApplicationListener listener : beansOfType.values()) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    public void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(Object event) {
        applicationEventMulticaster.multicastEvent((ApplicationEvent) event);
    }

    protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.setBeanClassLoader(this.getClassLoader());
    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    @SuppressWarnings({"AlibabaAvoidManuallyCreateThread"})
    @Override
    public void registerShutdownHook() {
        if (this.shutdownHook == null) {
            this.shutdownHook = new Thread(SHUTDOWN_HOOK_THREAD_NAME) {
                @Override
                public void run() {
                    synchronized (startupShutdownMonitor) {
                        doClose();
                    }
                }
            };
            Runtime.getRuntime().addShutdownHook(shutdownHook);
        }
    }

    @Override
    public void close() {
        synchronized (startupShutdownMonitor) {
            doClose();
            if (this.shutdownHook != null) {
                try {
                    Runtime.getRuntime().removeShutdownHook(this.shutdownHook);
                } catch (IllegalStateException ex) {
                    // ignore - VM is already shutting down
                }
            }
        }
    }

    public void doClose() {
        // 发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));
        getBeanFactory().destroySingletons();
    }

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beansOfType = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beansOfType.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beansOfType = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beansOfType.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public int getBeanDefinitionCount() {
        return getBeanFactory().getBeanDefinitionCount();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return getBeanFactory().getBeanNamesForType(type);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
        return getBeanFactory().getBeanNamesForAnnotation(annotationType);
    }

    @Override
    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
        return getBeanFactory().getBeansWithAnnotation(annotationType);
    }

    @Override
    public <T> T getBean(Class<T> beanClass, Object... args) {
        return getBeanFactory().getBean(beanClass, args);
    }

    @Override
    public Object getBean(String beanName, Object... args) {
        return getBeanFactory().getBean(beanName, args);
    }
}
