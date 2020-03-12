package com.customized.libs.core.libs.rocketmq.consumer;

import com.customized.libs.core.libs.rocketmq.MQEvent;
import com.customized.libs.core.libs.rocketmq.MQTags;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yan
 */
@Component("CustomizedLibsConsumer")
@Slf4j
public class CustomizedLibConsumer extends AbstractCustomizeMQConsumer {

    @Override
    public ConsumeConcurrentlyStatus onEvent(MQEvent event) {
        if (MQTags.CUSTOMIZED_LIB.equals(event.getTags())) {
            log.warn(">>> On Event Data ===> " + this.
                    parseMessage2Object((byte[]) event.getData(), Map.class));
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
