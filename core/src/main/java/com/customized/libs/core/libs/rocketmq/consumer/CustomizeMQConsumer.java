package com.customized.libs.core.libs.rocketmq.consumer;

import com.customized.libs.core.libs.rocketmq.MQEvent;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;

/**
 * @author yan
 */
public interface CustomizeMQConsumer {


    /**
     * MQ事件处理核心业务
     *
     * @param event
     * @return
     */
    ConsumeConcurrentlyStatus onEvent(MQEvent event);
}
