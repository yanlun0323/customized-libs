package com.customized.libs.core.libs.listener;

import com.customized.libs.core.utils.CommonUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author yan
 */
public class WatchDogs implements Runnable {

    private final List<DataChangedListener> listeners;

    public WatchDogs() {
        this.listeners = new CopyOnWriteArrayList<>();
    }

    /**
     * 可被动监听触发事件
     *
     * @param data
     */
    public void callListeners(Object data) {
        for (DataChangedListener listener : listeners) {
            listener.changed(data);
        }
    }

    @SuppressWarnings({"AlibabaAvoidManuallyCreateThread"})
    public void startListener() {
        Thread daemon = new Thread(this);
        daemon.setDaemon(true);
        daemon.setName("watch-dogs-thread");
        daemon.start();
    }

    public void addListener(DataChangedListener listener) {
        this.listeners.add(listener);
    }

    @SneakyThrows
    @Override
    public void run() {
        // 监听数据变化，changed状态控制器，由另外一个监听线程更改状态
        while (true) {
            // 模拟主动拉取数据触发监听事件
            TimeUnit.MILLISECONDS.sleep(RandomUtils.nextInt(100, 2000));
            this.callListeners(String.format("%s:%s",
                    DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),
                    RandomStringUtils.randomAlphanumeric(20))
            );
        }
    }

    public static void main(String[] args) throws IOException {
        WatchDogs watch = new WatchDogs();
        watch.addListener(data -> System.out.println(CommonUtils.buildLogString(data.toString(), "#", 16)));
        watch.startListener();

        System.in.read();
    }
}
