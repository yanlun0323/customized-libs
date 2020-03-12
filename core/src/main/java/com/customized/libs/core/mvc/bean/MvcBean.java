package com.customized.libs.core.mvc.bean;

import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yan
 */
public class MvcBean {

    private String path;

    private String contentType;

    private Class<?> targetClass;

    private Method targetMethod;

    private Object target;

    private ApplicationContext context;

    public String getPath() {
        return path;
    }

    public Object run(Object... args)
            throws InvocationTargetException, IllegalAccessException {
        if (target == null) {
            // SpringIOC容器获取Bean
            target = context.getBean(targetClass);
        }
        return targetMethod.invoke(target, args);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(Method targetMethod) {
        this.targetMethod = targetMethod;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
