package com.customized.rpc.core.bean.registry;

import com.customized.rpc.core.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yan
 */
@SuppressWarnings("unchecked")
public class ServiceRegistry implements ApplicationContextAware {

    private static Logger log = LoggerFactory.getLogger(ServiceRegistry.class);

    /**
     * 根据ClassName注册服务
     */
    private static final Map<String, Object> SERVICES = new HashMap<>(128);

    public static <T> T getService(String className) {
        return (T) SERVICES.get(className);
    }

    /**
     * 静态方法服务注册
     *
     * @param interfaceClass
     * @param implClass
     */
    public static void doRegister(Class<?> interfaceClass, Class<?> implClass) {
        try {
            SERVICES.putIfAbsent(interfaceClass.getCanonicalName(), implClass.newInstance());
            log.info("Interface(" + interfaceClass.getName() + ") Register Success! Impl Class(" + implClass.getName() + ")");
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Interface(" + interfaceClass.getName() + ") Register Failed!!", e);
        }
    }

    /**
     * 基于Spring动态注入
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> services = applicationContext.getBeansWithAnnotation(Service.class);
        if (services.size() > 0) {
            for (Object service : services.values()) {
                String interfaceName = service.getClass().getAnnotation(Service.class).value().getName();
                SERVICES.putIfAbsent(interfaceName, service);
                log.info("==> Register Service({})", interfaceName);
            }
        }
    }
}
