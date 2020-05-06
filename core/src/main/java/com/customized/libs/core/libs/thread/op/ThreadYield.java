package com.customized.libs.core.libs.thread.op;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author yan
 */
public class ThreadYield {

    /**
     * Java Thread创建的三种方式，以及Thread Yield使用案例
     */
    @SuppressWarnings("all")
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Runnable runnable = () -> {
            for (int i = 0; i <= 100; i++) {
                System.out.println(Thread.currentThread().getName() + "-----" + i);
                if (i % 20 == 0) {
                    Thread.yield();
                }
            }
        };


        // 1、基于FutureTask实现Thread
        FutureTask task = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                return "this is callback";
            }
        });
        new Thread(task).start();

        System.out.println(task.get());

        // 2、基于Runnable实现Thread，本质与FutureTask一样
        new Thread(runnable, "栈长").start();
        new Thread(runnable, "小蜜").start();

        // 3、继承Thread，自定义实现（多线程数据预处理）
        // 针对大量数据的数据处理，可通过CompletableFuture分片式预处理数据
        ThreadChild child = new ThreadChild((data) -> {
            return data.replace("child", "Child");
        }, "Thread child handle");

        child.start();
    }

    public static class ThreadChild extends Thread {

        private ThreadTaskFuture<String> taskFuture;

        private String data;

        public ThreadChild(ThreadTaskFuture<String> taskFuture, String data) {
            this.taskFuture = taskFuture;
            this.data = data;
        }

        @Override
        public void run() {
            Object data = this.taskFuture.handle(this.data);
            System.out.println(data);
        }
    }
}
