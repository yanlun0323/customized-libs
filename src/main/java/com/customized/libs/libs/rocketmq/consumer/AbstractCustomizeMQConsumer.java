package com.customized.libs.libs.rocketmq.consumer;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yan
 */
public abstract class AbstractCustomizeMQConsumer implements CustomizeMQConsumer {

    private static Logger logger = LoggerFactory.getLogger(AbstractCustomizeMQConsumer.class);

    <T> T parseMessage2Object(byte[] body, Class<T> classType) {
        T obj = null;
        try {
            obj = JSON.parseObject(body, classType);
        } catch (Throwable th) {
            logger.error("<<< Parse Json Text On Error", th);
        }
        return obj;
    }
}
