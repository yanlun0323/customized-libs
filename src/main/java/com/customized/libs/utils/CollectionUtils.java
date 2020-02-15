package com.customized.libs.utils;

import com.customized.libs.libs.entity.Keys;
import com.customized.libs.libs.entity.KeysTarget;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.function.Supplier;

/**
 * @author yan
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection<?> dataSource) {
        return null == dataSource || dataSource.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> dataSource) {
        return !isEmpty(dataSource);
    }

    public static <T> T findFirst(List<T> dataSource) {
        return CollectionUtils.isNotEmpty(dataSource) ? dataSource.get(0) : null;
    }

    /**
     * V0.1版本集合复制方法
     *
     * @param dataSource
     * @param targetSource
     * @param targetClazz
     * @param <T>
     * @param <E>
     */
    @SuppressWarnings("unchecked")
    public static <T, E> void copyCollections(List<T> dataSource, List<E> targetSource, Class<?> targetClazz) {
        for (T obj : dataSource) {
            try {
                E rsp = (E) targetClazz.newInstance();
                BeanUtils.copyProperties(obj, rsp);
                targetSource.add(rsp);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        List<Keys> dt = new ArrayList<>();
        dt.add(Keys.builder().k("Key").v("Value").build());
        dt.add(Keys.builder().k("Key2").v("Value2").build());

        List<KeysTarget> target = CollectionUtils.translateCollection(dt, KeysTarget::new);
        System.out.println(target);
    }

    /**
     * V1.0-转换Collection包含的对象类型
     *
     * @param dataSource
     * @param targetClazz
     * @param ignoreProperties
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T, E> List<E> translateCollection(List<T> dataSource, Class<E> targetClazz, String... ignoreProperties) {
        if (isNotEmpty(dataSource)) {
            List<E> targetSource = new ArrayList<>(20);
            for (T obj : dataSource) {
                try {
                    E rsp = targetClazz.newInstance();
                    BeanUtils.copyProperties(obj, rsp, ignoreProperties);
                    targetSource.add(rsp);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return targetSource;
        } else {
            return Collections.emptyList();
        }
    }

    public static <T, E> List<E> translateCollection(List<T> dataSource, Supplier<E> target, String... ignoreProperties) {
        return translateCollection(dataSource, target, null, ignoreProperties);
    }

    public static <T, E> List<E> translateCollection(List<T> dataSource, Supplier<E> target
            , CollectionCallback<T, E> callback, String... ignoreProperties) {
        if (isNotEmpty(dataSource)) {
            List<E> targetSource = new ArrayList<>(20);
            for (T obj : dataSource) {
                E rsp = target.get();
                BeanUtils.copyProperties(obj, rsp, ignoreProperties);
                targetSource.add(rsp);
                if (Objects.nonNull(callback)) {
                    callback.onResult(obj, rsp);
                }
            }
            return targetSource;
        } else {
            return Collections.emptyList();
        }
    }
}
