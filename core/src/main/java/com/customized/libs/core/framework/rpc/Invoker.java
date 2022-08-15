package com.customized.libs.core.framework.rpc;

import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.Method;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/6/14 17:15
 */
public class Invoker {

    private final String methodName;
    private final Object service;
    private final Class<?>[] parameterTypes;

    public Invoker(Object service, String methodName) {
        this.service = service;
        this.methodName = methodName;
        this.parameterTypes = ReflectUtil
                .getMethodByName(service.getClass(), methodName).getParameterTypes();
    }

    public Object invoke(Object[] arguments) throws Exception {
        Method method = service.getClass().getMethod(methodName, parameterTypes);
        System.out.println("Method " + methodName + " invoked!");
        return method.invoke(service, arguments);
    }
}
