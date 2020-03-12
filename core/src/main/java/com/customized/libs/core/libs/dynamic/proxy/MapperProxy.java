package com.customized.libs.core.libs.dynamic.proxy;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

public class MapperProxy<T> implements InvocationHandler, Serializable {

    private static final long serialVersionUID = -4340228288713885086L;

    private final SqlSession sqlSesssion;

    /**
     * Mybatis的mapper接口动态代理
     */
    private final Class<T> mapperInterface;
    private final Map<Method, MapperMethod> methodCache;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface, Map<Method, MapperMethod> methodCache) {
        this.sqlSesssion = sqlSession;
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperMethod mapperMethod = this.getCachedMapperMethod(method);
        return mapperMethod.execute(sqlSesssion, args);
    }

    private MapperMethod getCachedMapperMethod(Method method) {
        MapperMethod cachedMethod = this.methodCache.get(method);
        if (Objects.isNull(cachedMethod)) {
            cachedMethod = new MapperMethod(mapperInterface, method);
            methodCache.put(method, cachedMethod);
        } else {
            System.out.println("\r\nMethod ==> " + method.getName() + " from cache!!\r\n");
        }
        return cachedMethod;
    }
}
