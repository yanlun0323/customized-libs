package com.customized.libs.core.libs.javase.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore 翻译成字面意思为 信号量，Semaphore 可以控制同时访问的线程个数，通过
 * acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。
 * <p>
 * <p>
 * 例子:若一个工厂有 5 台机器，但是有 8 个工人，一台机器同时只能被一个工人使用，只有使用完
 * 了，其他工人才能继续使用。那么我们就可以通过 Semaphore 来实现:
 *
 * @author yan
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        Machine machine = new Machine(5);
        for (int i = 0; i < 8; i++) {
            Worker worker = new Worker();
            worker.setWorkerNo(i + 1);
            worker.setMachine(machine);
            worker.start();
        }
    }

    public static class Machine {

        private Semaphore semaphore;

        private Integer number;

        Machine(Integer number) {
            this.number = number;
            this.semaphore = new Semaphore(this.number);
        }

        public void apply() throws InterruptedException {
            semaphore.acquire();
        }

        public void release() {
            semaphore.release();
        }

        public Semaphore getSemaphore() {
            return semaphore;
        }

        public void setSemaphore(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }
    }

    public static class Worker extends Thread {

        private Machine machine;

        private Integer workerNo;

        public Machine getMachine() {
            return machine;
        }

        public void setMachine(Machine machine) {
            this.machine = machine;
        }

        public Integer getWorkerNo() {
            return workerNo;
        }

        public void setWorkerNo(Integer workerNo) {
            this.workerNo = workerNo;
        }

        @Override
        public void run() {
            try {
                this.machine.apply();

                System.out.println("工人" + this.getWorkerNo() + "占用一个机器在生产...");
                TimeUnit.SECONDS.sleep(2);
                System.out.println("工人" + this.getWorkerNo() + "释放出机器");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.machine.release();
            }
        }
    }
}
