package com.customized.libs.libs.atomic;

import org.apache.commons.lang3.RandomUtils;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger counter0 = new AtomicInteger(0);

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(RandomUtils.nextInt(0, 10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter0.incrementAndGet();
            }).start();
        }

        Thread.sleep(1000);
        System.out.println("data-0 =>> " + counter0.get());

        MultiThreadCounter threadCounter = new MultiThreadCounter();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(RandomUtils.nextInt(0, 10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                threadCounter.incrementAndGet();
            }).start();
        }

        Thread.sleep(1000);
        System.out.println("data-x =>> " + threadCounter.getTotal());
    }

    static class MultiThreadCounter {

        private static Unsafe unsafe;
        // private static Unsafe unsafe = UnsafeUtils.getUnsafe();
        private static long valueOffset;

        static {
            try {
                final PrivilegedExceptionAction<Unsafe> action = () -> {
                    Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                    theUnsafe.setAccessible(true);
                    return (Unsafe) theUnsafe.get(null);
                };

                unsafe = AccessController.doPrivileged(action);
            } catch (Exception e) {
                throw new RuntimeException("Unable to load unsafe", e);
            }
        }

        static {
            try {
                valueOffset = unsafe.objectFieldOffset
                        (MultiThreadCounter.class.getDeclaredField("total"));

                Field[] fields = MultiThreadCounter.class.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    if (!"unsafe".equals(fields[i].getName())
                            && !"valueOffset".equals(fields[i].getName())) {
                        System.out.println(fields[i].getType() + " Field Offset ==> " + unsafe.objectFieldOffset(fields[i]));
                    }
                }
            } catch (Exception ex) {
                throw new Error(ex);
            }
        }

        private volatile int total;
        private volatile long longField;
        private volatile boolean booleanField;
        private volatile Boolean booleanObjField;
        private volatile byte byteField;
        private volatile char charField;
        private volatile float floatField;
        private volatile double doubleField;

        final int incrementAndGet() {
            return unsafe.getAndAddInt(this, valueOffset, 1) + 1;
        }

        public MultiThreadCounter() {
            total = 0;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }
    }
}
