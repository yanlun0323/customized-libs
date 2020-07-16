package com.customized.libs.core.libs.threadpool;

import com.customized.libs.core.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    public static void main(String[] args) {
        BlockingQueue queue = new ArrayBlockingQueue(16);
        CustomThreadPool pool = new CustomThreadPool(4, 8, 1, TimeUnit.SECONDS
                , queue, () -> System.out.println("任务执行完毕。。。"));

        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            int finalI = i;
            Future<Integer> future = pool.submit(() -> finalI);
            futures.add(future);
        }

        pool.shutdown();

        System.out.println(CommonUtils.buildLogString(" ############## ", "*", 32));

        pool.mainNotify();

        futures.forEach(c -> {
            Integer data = null;
            try {
                data = (Integer) c.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("==> data = " + data);
        });
        System.out.println(CommonUtils.buildLogString(" ############## ", "*", 32));
    }
}
