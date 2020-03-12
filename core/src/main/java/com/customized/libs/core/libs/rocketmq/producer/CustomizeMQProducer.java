package com.customized.libs.core.libs.rocketmq.producer;

import com.customized.libs.core.libs.rocketmq.MQEvent;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

/**
 * @author yan
 */
public interface CustomizeMQProducer {

    /**
     * 消息事件发送
     *
     * @param event
     */
    void sendEvent(MQEvent event);

    /**
     * 获取消息生产者对象
     *
     * @return
     */
    DefaultMQProducer getProducer();

    /**
     * MQ Topic
     *
     * @return
     */
    String getTopic();

    /**
     * MQ Topic标签
     *
     * @return
     */
    String getTopicTag();

    /**
     * 消息Keys构建
     *
     * @param keySuffix
     * @return
     */
    default String buildKeys(String keySuffix) {
        return String.format("%s:%s:%s", this.getTopic(), this.getTopicTag(), keySuffix);
    }
}
