package com.customized.libs.springboot.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yan
 */
@Service
public class BatchQueryService {

    public static final Map<String, Object> DB = new HashMap<>();

    static {
        for (int i = 0; i < 10; i++) {
            DB.put(String.format("K:%s", i), String.format("V:%s", i));
        }
    }

    /**
     * //请求合并是允许的最大请求数,默认: Integer.MAX_VALUE
     * private final HystrixProperty maxRequestsInBatch;
     * //批处理过程中每个命令延迟的时间,默认:10毫秒
     * private final HystrixProperty timerDelayInMilliseconds;
     * //批处理过程中是否开启请求缓存,默认:开启
     * private final HystrixProperty requestCacheEnabled;
     * <p>
     * 此处配置timerDelayInMilliseconds:2000 配置批处理过程中每个命令延迟2s（即2秒后客户端收到响应）
     *
     * @param id
     * @return
     */
    @HystrixCollapser(batchMethod = "queryBatchByIds", scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,
            collapserProperties = {
                    @HystrixProperty(name = "maxRequestsInBatch", value = "10"),
                    @HystrixProperty(name = "timerDelayInMilliseconds", value = "2000")
            }
    )
    public Object queryById(String id) {
        System.out.println("Query By Single Key");
        return DB.getOrDefault(id, null);
    }

    @HystrixCommand
    public List<Object> queryBatchByIds(List<String> ids) {
        System.out.println("Query By Batch Key ==> " + ids);
        return DB.entrySet().stream().filter(c -> ids.contains(c.getKey())).collect(Collectors.toList());
    }
}
