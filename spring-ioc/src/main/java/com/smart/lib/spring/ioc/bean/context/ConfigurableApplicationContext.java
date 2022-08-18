package com.smart.lib.spring.ioc.bean.context;

import com.smart.lib.spring.ioc.bean.exception.BeansException;

import java.io.Closeable;

public interface ConfigurableApplicationContext extends ApplicationContext, Closeable {

    String SHUTDOWN_HOOK_THREAD_NAME = "SpringContextShutdownHook";

    /**
     * 容齐刷新，提供bean扫描注册等功能
     */
    void refresh() throws BeansException;

    /**
     * Register a shutdown hook with the JVM runtime, closing this context
     * on JVM shutdown unless it has already been closed at that time.
     * <p>This method can be called multiple times. Only one shutdown hook
     * (at max) will be registered for each context instance.
     * <p>As of Spring Framework 5.2, the {@linkplain Thread#getName() name} of
     * the shutdown hook thread should be {@link #SHUTDOWN_HOOK_THREAD_NAME}.
     *
     * @see java.lang.Runtime#addShutdownHook
     * @see #close()
     */
    void registerShutdownHook();

    /**
     * Close this application context, releasing all resources and locks that the
     * implementation might hold. This includes destroying all cached singleton beans.
     * <p>Note: Does <i>not</i> invoke {@code close} on a parent context;
     * parent contexts have their own, independent lifecycle.
     * <p>This method can be called multiple times without side effects: Subsequent
     * {@code close} calls on an already closed context will be ignored.
     */
    @Override
    void close();
}
