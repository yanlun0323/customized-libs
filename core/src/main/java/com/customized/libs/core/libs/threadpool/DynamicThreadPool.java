package com.customized.libs.core.libs.threadpool;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.customized.libs.core.utils.threadpool.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author yan
 */
@Component
public class DynamicThreadPool implements InitializingBean {

    private static Logger log = LoggerFactory.getLogger(DynamicThreadPool.class);

    /**
     * 缓存多个Executor
     */
    private static final Map<String, Executor> EXECUTOR_CACHE = new ConcurrentHashMap<>(32);

    private static final String CORE_POOL_SIZE_KEY = "corePoolSize";
    private static final String MAXIMUM_POOL_SIZE_KEY = "maximumPoolSize";

    private static final String DEFAULT = "DEFAULT";
    private static final String TASK = "TASK";

    @Value("${" + CORE_POOL_SIZE_KEY + "}")
    private Integer corePoolSize;

    @Value("${" + MAXIMUM_POOL_SIZE_KEY + "}")
    private Integer maximumPoolSize;

    @Value("${sentinel.dashboard}")
    private String dashboard;

    @Override
    public void afterPropertiesSet() {
        this.initialization();
    }

    private void initialization() {
        EXECUTOR_CACHE.put(DEFAULT, new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(512),
                new NamedThreadFactory("default-executor"),
                new ThreadPoolExecutor.CallerRunsPolicy()
        ));

        EXECUTOR_CACHE.put(TASK, new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(512),
                new NamedThreadFactory("task-executor"),
                new ThreadPoolExecutor.CallerRunsPolicy()
        ));

        this.registerConfigChangeListener();
    }

    private void registerConfigChangeListener() {
        Config config = ConfigService.getAppConfig();
        config.addChangeListener(changeEvent -> {
            log.warn("Changes for namespace " + changeEvent.getNamespace());
            for (String key : changeEvent.changedKeys()) {
                ConfigChange change = changeEvent.getChange(key);
                log.warn(String.format(
                        "Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s",
                        change.getPropertyName(), change.getOldValue(),
                        change.getNewValue(), change.getChangeType())
                );
                EXECUTOR_CACHE.forEach((eKey, value) -> {
                    ThreadPoolExecutor executor = (ThreadPoolExecutor) value;
                    switch (change.getPropertyName()) {
                        case CORE_POOL_SIZE_KEY:
                            executor.setCorePoolSize(Integer.parseInt(change.getNewValue()));
                            break;
                        case MAXIMUM_POOL_SIZE_KEY:
                            executor.setMaximumPoolSize(Integer.parseInt(change.getNewValue()));
                            break;
                        default:
                            // ignored
                    }
                });
            }
        });
    }

    public Executor getExecutor(String type) {
        return getOrDefault(type, DEFAULT);
    }

    private Executor getOrDefault(String type, String defaultType) {
        return EXECUTOR_CACHE.getOrDefault(type, EXECUTOR_CACHE.get(defaultType));
    }
}
