package com.customized.libs.libs.dynamic.proxy;

import java.lang.reflect.Method;

/**
 * @author yan
 */
public class MapperMethod {

    private Class<?> mapperInterface;
    private Method method;

    public MapperMethod(Class<?> mapperInterface, Method method) {
        this.mapperInterface = mapperInterface;
        this.method = method;
    }

    public Object execute(SqlSession sqlSession, Object[] args) {
        Object result;
        // 获取对应MapperId，找到对应的SQL脚本
        String statementName = mapperInterface.getName() + "." + method.getName();
        result = sqlSession.executeSQL(statementName);
        return result;
    }
}
