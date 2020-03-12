package com.customized.libs.core.utils;

/**
 * @author yan
 */
@FunctionalInterface
public interface CollectionCallback<S, T> {

    /**
     * 接收回调
     *
     * @param t
     * @param s
     */
    void onResult(S t, T s);
}