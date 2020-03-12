package com.customized.libs.core.libs.rocketmq.producer;

import com.customized.libs.core.libs.rocketmq.MQTags;
import com.customized.libs.core.libs.rocketmq.MQTopics;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yan
 */
@Service
public class CustomizedLibsProducer extends AbstractCustomizeMQProducer {

    @Resource(name = "CustomizedLibsProducer")
    private DefaultMQProducer defaultProducer;

    @Override
    public DefaultMQProducer getProducer() {
        return this.defaultProducer;
    }

    @Override
    public String getTopic() {
        return MQTopics.CUSTOMIZED_LIB;
    }

    @Override
    public String getTopicTag() {
        return MQTags.CUSTOMIZED_LIB;
    }
}