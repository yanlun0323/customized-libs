package com.customized.libs.core.libs.interview;

import org.apache.commons.lang.StringUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yan
 */
public class TwoThreadWithReentrantLock {

    private volatile int num = 0;

    private ReentrantLock lock = new ReentrantLock();

    /**
     * even - 偶数；odd - 奇数
     */
    private Condition evenCondition = lock.newCondition();
    private Condition oddCondition = lock.newCondition();

    private int max;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    /**
     * 此处调用await方法，若调用Object内的wait方法，抛出异常（Exception in thread "[WARN] Odd-Thread" java.lang.IllegalMonitorStateException）
     * <p>
     * 为什么会出现这样的异常呢？
     * 1、解除那些在该对象上调用wait()、notify()、notifyAll()方法的线程的阻塞状态。该方法只能在同步方法或同步块内部调用。如果当前线程不是对象所得持有者，该方法抛出一个java.lang.IllegalMonitorStateException 异常
     * <p>
     * 例如：我用this对象调用的wait方法或者notify方法（如：this.notify()），那么必须保证，运行的线程拥有this对象锁（如synchronized(this)），而他解除的是除当前线程之外的任一其他线程的阻塞状态。
     * <p>
     * 确切地说，应该是调用waint(),notify(),notifyAll()的线程必须拥有作为monitor对象的同步锁。
     * ————————————————
     * 版权声明：本文为CSDN博主「小码农eve」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/cdw8131197/article/details/52434908
     * <p>
     * <p>
     * Java condition await signal signalall对比wait notify notifyall
     * <p>
     * https://www.cnblogs.com/CreatorKou/p/9829350.html
     *
     * @throws InterruptedException
     */
    public void printOddNumber() throws InterruptedException {
        while (num <= max) {
            lock.lock();
            try {
                String beautifyTag = StringUtils.rightPad("Thread(" + Thread.currentThread().getName() + ")", 32);
                System.out.println(beautifyTag + " ==> " + num++);
                this.evenCondition.signal();
                this.oddCondition.await();
            } finally {
                lock.unlock();
            }
        }
    }

    public void printEvenNumber() throws InterruptedException {
        while (num <= max) {
            lock.lock();
            try {
                String beautifyTag = StringUtils.rightPad("Thread(" + Thread.currentThread().getName() + ")", 32);
                System.out.println(beautifyTag + " ==> " + num++);
                this.oddCondition.signal();
                this.evenCondition.await();
            } finally {
                lock.unlock();
            }
        }
    }

    @SuppressWarnings("all")
    public static void main(String[] args) throws InterruptedException {
        TwoThreadWithReentrantLock reentrantLock = new TwoThreadWithReentrantLock();
        reentrantLock.setMax(100);

        Thread thread0 = new Thread(() -> {
            try {
                reentrantLock.printEvenNumber();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "[INFO] Even-Thread");
        thread0.start();

        TimeUnit.MILLISECONDS.sleep(100);

        Thread thread1 = new Thread(() -> {
            try {
                reentrantLock.printOddNumber();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "[WARN] Odd-Thread");
        thread1.start();
    }
}
