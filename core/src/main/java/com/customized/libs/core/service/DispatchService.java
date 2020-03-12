package com.customized.libs.core.service;

import com.alibaba.fastjson.JSON;
import com.customized.libs.core.libs.aop.redislock.annos.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author yan
 */
@Service
public class DispatchService {

    private static final Logger logger = LoggerFactory.getLogger(DispatchService.class);

    @RedisLock(key = "Dispatch-Service")
    public void dispatch(Map<String, Object> bizData) {
        logger.warn(">>> Dispatch Biz Data ==> {}", JSON.toJSONString(bizData));
    }

}
