package com.customized.libs.core.libs.cacheline;

public class FalseSharing implements Runnable {
    public final static long ITERATIONS = 500L * 1000L * 100L;
    private int arrayIndex = 0;

    private boolean padding;

    private static ValuePadding[] paddings;
    private static ValueNoPadding[] noPaddings;

    public FalseSharing(final int arrayIndex, boolean padding) {
        this.arrayIndex = arrayIndex;
        this.padding = padding;
    }

    public static void main(final String[] args) throws Exception {
        for (int i = 1; i < 10; i++) {
            System.gc();
            final long start = System.currentTimeMillis();
            runPaddingTest(i);
            System.out.println("Thread num " + i + " duration = " + (System.currentTimeMillis() - start));
        }
    }

    /**
     * Thread num 1 duration = 485
     * Thread num 2 duration = 1667
     * Thread num 3 duration = 1387
     * Thread num 4 duration = 1313
     * Thread num 5 duration = 2868
     * Thread num 6 duration = 2978
     * Thread num 7 duration = 2394
     * Thread num 8 duration = 3061
     * Thread num 9 duration = 2570
     *
     * @param threadNum
     * @throws InterruptedException
     */
    private static void runNoPaddingTest(int threadNum) throws InterruptedException {
        Thread[] threads = new Thread[threadNum];
        noPaddings = new ValueNoPadding[threadNum];
        for (int i = 0; i < noPaddings.length; i++) {
            noPaddings[i] = new ValueNoPadding();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharing(i, Boolean.FALSE));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }

    /**
     * Thread num 1 duration = 559
     * Thread num 2 duration = 796
     * Thread num 3 duration = 914
     * Thread num 4 duration = 914
     * Thread num 5 duration = 906
     * Thread num 6 duration = 1158
     * Thread num 7 duration = 1237
     * Thread num 8 duration = 1531
     * Thread num 9 duration = 1451
     *
     * @param threadNum
     * @throws InterruptedException
     */
    private static void runPaddingTest(int threadNum) throws InterruptedException {
        Thread[] threads = new Thread[threadNum];
        paddings = new ValuePadding[threadNum];
        for (int i = 0; i < paddings.length; i++) {
            paddings[i] = new ValuePadding();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharing(i, Boolean.TRUE));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }

    @Override
    public void run() {
        long i = ITERATIONS + 1;
        if (padding) {
            while (0 != --i) {
                paddings[arrayIndex].value = 0L;
            }
        } else {
            while (0 != --i) {
                noPaddings[arrayIndex].value = 0L;
            }
        }
    }

    public final static class ValuePadding {
        protected long p1, p2, p3, p4, p5, p6, p7;
        protected volatile long value = 0L;
        protected long p9, p10, p11, p12, p13, p14;
        protected long p15;
    }

    /**
     * JVM参数：-XX:-RestrictContended
     */
    // @Contended
    public final static class ValueNoPadding {
        protected volatile long value = 0L;
    }
}