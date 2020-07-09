package com.customized.libs.core.libs.limiter;

public class TimeWindowCalculateDemo {

    public static void main(String[] args) throws InterruptedException {
        int windowLength = 200;
        int arrayLength = 10;
        calculate(windowLength, arrayLength);

        Thread.sleep(100);
        calculate(windowLength, arrayLength);

        Thread.sleep(200);
        calculate(windowLength, arrayLength);

        Thread.sleep(200);
        calculate(windowLength, arrayLength);

        Thread.sleep(500);
        calculate(windowLength, arrayLength);

        Thread.sleep(500);
        calculate(windowLength, arrayLength);

        Thread.sleep(500);
        calculate(windowLength, arrayLength);

        Thread.sleep(500);
        calculate(windowLength, arrayLength);

        Thread.sleep(500);
        calculate(windowLength, arrayLength);
    }

    private static void calculate(int windowLength, int arrayLength) {
        long time = System.currentTimeMillis();
        long timeId = time / windowLength;
        long currentWindowStart = time - time % windowLength;
        int idx = (int) (timeId % arrayLength);
        System.out.println("time=" + time + ",currentWindowStart=" + currentWindowStart + ",timeId=" + timeId + ",idx=" + idx);
    }

}
