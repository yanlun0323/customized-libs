package com.customized.libs.core.libs.distributed.id;

import com.customized.libs.core.utils.IdUtil;
import com.google.common.collect.Sets;

import java.util.Set;

public class IdUtilTest {
    public static void main(String[] args) throws InterruptedException {
        Set<Long> idSet = Sets.newConcurrentHashSet();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                idSet.add(IdUtil.nextId());
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                idSet.add(IdUtil.nextId());
            }
        });

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                idSet.add(IdUtil.nextId());
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("生成id完毕");
        System.out.println(idSet.size());
    }
}
