package com.customized.libs.core.libs.ioc;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yan
 * @version 1.0
 * @description 依赖注入测试案例
 * @date 2022/2/25 09:43
 */
public class DependenceTest {

    private static final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(32);

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) throws Exception {
        String beanName = clazz.getSimpleName();
        if (singletonObjects.containsKey(beanName)) {
            return (T) singletonObjects.get(beanName);
        }
        // 实例化
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();
            singletonObjects.put(beanName, obj);

            // 获取成员变量
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (!ClassUtils
                        .isPrimitiveOrWrapper(declaredField.getClass())) {
                    declaredField.setAccessible(Boolean.TRUE);
                    declaredField.set(obj, getBean(declaredField.getType()));
                }
            }
            return obj;
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            throw new Exception(e.getMessage());
        }
    }


    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        // 扫描并初始化bean
        Class<DependenceService>[] beans = new Class[]{
                DependenceServiceB.class, DependenceServiceA.class
        };
        for (Class<DependenceService> dependenceServiceClass : beans) {
            // get and init bean
            DependenceService bean = getBean(dependenceServiceClass);
            bean.execute();
        }
    }
}
