package com.customized.libs.core.libs.thread.op;

/**
 * @author yan
 */
@FunctionalInterface
public interface ThreadTaskFuture<T> {

    Object handle(T data);
}
