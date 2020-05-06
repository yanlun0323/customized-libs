package com.customized.libs.core.libs.thread.local;

public class ThreadSeq {

    /**
     * 可设置初始化值（ThreadLocal.withInitial）
     */
    private static ThreadLocal<Integer> seq = ThreadLocal.withInitial(() -> 0);

    public Integer getNextNum() {
        seq.set(seq.get() + 1);
        return seq.get();
    }
}
