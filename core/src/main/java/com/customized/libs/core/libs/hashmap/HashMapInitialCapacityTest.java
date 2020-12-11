package com.customized.libs.core.libs.hashmap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yan
 */
public class HashMapInitialCapacityTest {

    /**
     * 以2的N次方增长，遇到边界不改变
     *
     * @param args
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("AlibabaAvoidCommentBehindStatement")
    public static void main(String[] args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        getHashMapCapacity(15); // 2^4
        getHashMapCapacity(16); // 2^4
        getHashMapCapacity(17); // 2^5
        getHashMapCapacity(32); // 2^5
        getHashMapCapacity(33); // 2^6
    }

    private static void getHashMapCapacity(Integer initialCapacity)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Map<String, Object> map = new HashMap<>(initialCapacity);
        Method capacity = map.getClass().getDeclaredMethod("capacity");
        capacity.setAccessible(true);
        System.out.println("capacity : " + capacity.invoke(map));
    }
}
