package com.customized.libs.libs.rocketmq.consumer;

import com.customized.libs.libs.rocketmq.MQEvent;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * MQ消息监听容器
 *
 * @author yan
 */
@Component
public class MQConsumerContainer implements MessageListenerConcurrently {

    private static Logger logger = LoggerFactory.getLogger(MQConsumerContainer.class);

    private CustomizeMQConsumer customizeMQConsumer;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs
            , ConsumeConcurrentlyContext context) {
        MessageExt messageExt = msgs.get(0);
        logger.info("<<< Message Listener Receive Data ==> "
                + new String(messageExt.getBody()));

        MQEvent event = new MQEvent();
        event.setData(messageExt.getBody());
        event.setTags(messageExt.getTags());

        return this.customizeMQConsumer.onEvent(event);
    }

    public CustomizeMQConsumer getCustomizeMQConsumer() {
        return customizeMQConsumer;
    }

    public void setCustomizeMQConsumer(CustomizeMQConsumer customizeMQConsumer) {
        this.customizeMQConsumer = customizeMQConsumer;
    }
}
