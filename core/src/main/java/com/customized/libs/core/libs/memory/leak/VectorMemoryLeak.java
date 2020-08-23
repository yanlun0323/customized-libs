package com.customized.libs.core.libs.memory.leak;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class VectorMemoryLeak {

    public static void main(String[] args) throws InterruptedException {
        Vector<Object> vector = new Vector<>(64);

        for (int i = 0; i < 5000; i++) {
            Object o = new Object();
            vector.add(o);
            // o = null;
        }

        TimeUnit.MINUTES.sleep(25);
    }
}
