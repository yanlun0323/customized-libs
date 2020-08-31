package com.customized.libs.core.libs.threadpool.demo;

import com.customized.libs.core.libs.threadpool.DynamicThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yan
 */
@Component
public class DynamicThreadPoolTest {

    @Autowired
    private DynamicThreadPool dynamicThreadPool;

    @PostConstruct
    public void initTest() {

        ThreadPoolExecutor aDefault = (ThreadPoolExecutor) dynamicThreadPool.getExecutor("DEFAULT");
        for (int i = 0; i < 200; i++) {
            aDefault.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    // ignored
                }

                System.out.println(String.format("[ERROR] 核心线程数（%s），活动线程数（%s），最大线程数（%s）"
                        , aDefault.getCorePoolSize(), aDefault.getActiveCount(), aDefault.getMaximumPoolSize())
                );
            });
        }
    }
}
