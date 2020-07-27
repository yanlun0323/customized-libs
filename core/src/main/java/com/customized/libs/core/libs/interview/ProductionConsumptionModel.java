package com.customized.libs.core.libs.interview;

import com.customized.libs.core.utils.threadpool.CallerRunsPolicyWithReport;
import com.customized.libs.core.utils.threadpool.NamedThreadFactory;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yan
 */
public class ProductionConsumptionModel {

    private final ReentrantLock locker = new ReentrantLock();

    private static final String TAG = "MULTI_THREAD_CONSUMER";

    private static ExecutorService CORE_EXECUTORS = new ThreadPoolExecutor(128 * 4, 256 * 4, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1024),
            new NamedThreadFactory(TAG),
            new CallerRunsPolicyWithReport(TAG)
    );

    /**
     * 读线程信号条件
     * <p>
     * Not Empty Condition监听，当收到信号，则继续消费
     * <p>
     * 消费成功，则释放NotFull信号，可被生产者消费
     */
    private final Condition NOT_EMPTY = locker.newCondition();

    /**
     * 写线程信号
     * <p>
     * Not Full Condition监听，当收到信号，则继续创建
     * <p>
     * 创建成功，则释放NotEmpty信号，可被消费者消费
     */
    private final Condition NOT_FULL = locker.newCondition();


    /**
     * <p>
     * 1>PriorityQueue是一种无界的，线程不安全的队列
     * 2>PriorityQueue是一种通过数组实现的，并拥有优先级的队列
     * 3>PriorityQueue存储的元素要求必须是可比较的对象， 如果不是就必须明确指定比较器
     * <p>
     * 作者：叩丁狼教育
     * 链接：https://www.jianshu.com/p/f1fd9b82cb72
     * 来源：简书
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    private Queue<Object> cache = new PriorityQueue<>(64);

    private Integer maxSize;

    public ProductionConsumptionModel() {

    }

    public ProductionConsumptionModel(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public void put(Object x) throws InterruptedException {
        locker.lock();
        try {
            // 如果队列满，则阻塞<写线程>，等待读线程释放信号
            while (this.cache.size() == maxSize) {
                this.NOT_FULL.await();
            }

            cache.offer(x);

            // 唤醒<读线程>
            this.NOT_EMPTY.signal();
        } finally {
            locker.unlock();
        }
    }

    public Object take() throws InterruptedException {
        locker.lock();
        try {
            // 如果队列空，则阻塞<读线程>，等待写线程释放信号
            while (this.cache.size() == 0) {
                this.NOT_EMPTY.await();
            }
            Object data = cache.poll();

            // 唤醒<写线程>
            this.NOT_FULL.signal();
            return data;
        } finally {
            locker.unlock();
        }
    }

    /**
     * System.out / System.err是两个线程打印
     *
     * @param args
     */
    @SuppressWarnings("all")
    public static void main(String[] args) {
        Integer MAX_INDEX = 1000;
        ProductionConsumptionModel boundedBuffer = new ProductionConsumptionModel(512);

        // <写线程>
        Thread writeThread = new Thread(() -> {
            for (int i = 0; i < MAX_INDEX; i++) {
                try {
                    boundedBuffer.put(i);
                    System.out.println("==> Write Data: " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        writeThread.setName("Write-Thread");
        writeThread.start();


        // <读线程>
        Thread readThread = new Thread(() -> {
            for (int k = 0; k < MAX_INDEX; k++) {
                try {
                    System.err.println("==> Take Data: " + boundedBuffer.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        readThread.setName("Read-Thread");
        readThread.start();
    }
}