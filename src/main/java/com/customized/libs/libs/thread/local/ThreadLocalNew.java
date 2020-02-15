package com.customized.libs.libs.thread.local;

/**
 * @author yan
 */
public class ThreadLocalNew extends Thread {

    private ThreadSeq seq;

    public ThreadLocalNew() {
        this.seq = new ThreadSeq();
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("thread[" + Thread.currentThread().getName() + "] --> sn["
                    + seq.getNextNum() + "]");
        }
    }

    public ThreadSeq getSeq() {
        return seq;
    }

    public void setSeq(ThreadSeq seq) {
        this.seq = seq;
    }

    public static void main(String[] args) {
        ThreadLocalNew threadLocalNew0 = new ThreadLocalNew();
        threadLocalNew0.start();

        ThreadLocalNew threadLocalNew1 = new ThreadLocalNew();
        threadLocalNew1.start();

        ThreadLocalNew threadLocalNew2 = new ThreadLocalNew();
        threadLocalNew2.start();
    }
}
