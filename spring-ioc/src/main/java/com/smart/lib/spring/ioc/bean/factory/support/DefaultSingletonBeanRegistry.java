package com.smart.lib.spring.ioc.bean.factory.support;

import com.smart.lib.spring.ioc.bean.factory.DisposableBean;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/15 11:08
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    protected final Object NULL_OBJECT = null;

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    /**
     * Disposable bean instances: bean name to disposable instance.
     */
    private final Map<String, DisposableBean> disposableBeans = new LinkedHashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        this.addSingleton(beanName, singletonObject);
    }

    /**
     * Remove the bean with the given name from the singleton cache of this factory,
     * to be able to clean up eager registration of a singleton if creation failed.
     *
     * @param beanName the name of the bean
     */
    protected void removeSingleton(String beanName) {
        synchronized (this.singletonObjects) {
            this.singletonObjects.remove(beanName);
        }
    }


    @Override
    public void destroySingletons() {
        String[] beanNames = disposableBeans.keySet().toArray(new String[0]);
        for (String beanName : beanNames) {
            destroyBean(beanName);
        }
    }

    public void destroyBean(String beanName) {
        DisposableBean disposableBean;
        synchronized (this.disposableBeans) {
            disposableBean = this.disposableBeans.remove(beanName);
        }
        try {
            disposableBean.destroy();
        } catch (Exception exception) {
            // ignored
        }
        removeSingleton(beanName);
    }

    /**
     * Add the given bean to the list of disposable beans in this registry.
     * <p>Disposable beans usually correspond to registered singletons,
     * matching the bean name but potentially being a different instance
     * (for example, a DisposableBean adapter for a singleton that does not
     * naturally implement Spring's DisposableBean interface).
     *
     * @param beanName the name of the bean
     * @param bean     the bean instance
     */
    public void registerDisposableBean(String beanName, DisposableBean bean) {
        synchronized (this.disposableBeans) {
            this.disposableBeans.put(beanName, bean);
        }
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjects) {
            singletonObjects.put(beanName, singletonObject);
        }
    }
}
