package com.customized.libs.libs.rocketmq.consumer;

import com.customized.libs.libs.rocketmq.MQEvent;
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
