package com.customized.libs.libs.thread.local;

public class ThreadSeq {

    private static ThreadLocal<Integer> seq = ThreadLocal.withInitial(() -> 0);

    public Integer getNextNum() {
        seq.set(seq.get() + 1);
        return seq.get();
    }
}
