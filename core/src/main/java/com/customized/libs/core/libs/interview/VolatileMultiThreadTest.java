package com.customized.libs.core.libs.interview;

import org.apache.commons.lang.StringUtils;

public class VolatileMultiThreadTest {

    private volatile boolean FLAG = Boolean.TRUE;

    private Integer maxNumber;

    private Integer currNumber;


    public Integer getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(Integer maxNumber) {
        this.maxNumber = maxNumber;
    }

    public Integer getCurrNumber() {
        return currNumber;
    }

    public void setCurrNumber(Integer currNumber) {
        this.currNumber = currNumber;
    }

    /**
     * 奇数打印
     */
    public void oddPrint() {
        while (currNumber <= maxNumber) {
            doPrintNumber(!this.FLAG);
        }
    }


    public void evenPrint() {
        while (currNumber <= maxNumber) {
            doPrintNumber(this.FLAG);
        }
    }

    private void doPrintNumber(Boolean flag) {
        if (Boolean.TRUE.equals(flag)) {
            String beautifyTag = StringUtils.rightPad("Thread(" + Thread.currentThread().getName() + ")", 32);
            System.out.println(beautifyTag + " ==> " + currNumber);
            currNumber++;
            this.FLAG = !this.FLAG;
        }
    }

    @SuppressWarnings("all")
    public static void main(String[] args) {
        VolatileMultiThreadTest threadTest = new VolatileMultiThreadTest();
        threadTest.setCurrNumber(0);
        threadTest.setMaxNumber(100);

        // Thread0
        Thread oddThread = new Thread(() -> {
            threadTest.oddPrint();
        });
        oddThread.setName("[WARN] Odd-Thread");
        oddThread.start();


        // thread1
        Thread evenThread = new Thread(() -> {
            threadTest.evenPrint();
        });
        evenThread.setName("[INFO] Even-Thread");
        evenThread.start();
    }
}
