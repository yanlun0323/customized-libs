package com.customized.libs.libs.rocketmq.producer;

import com.alibaba.fastjson.JSON;
import com.customized.libs.libs.rocketmq.MQEvent;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yan
 */
public abstract class AbstractCustomizeMQProducer implements CustomizeMQProducer {

    private static Logger logger = LoggerFactory.getLogger(AbstractCustomizeMQProducer.class);

    @Override
    public void sendEvent(MQEvent event) {
        logger.info("<<< MQ Event: " + JSON.toJSONString(event));
        String key = this.buildKeys(event.getKeySuffix());
        try {
            Message message = new Message(this.getTopic(), this.getTopicTag(), key,
                    JSON.toJSONBytes(event.getData()));

            this.getProducer().send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    logger.warn("<<< MQ Event (key=" + key + ") Message To MQ Success");
                }

                @Override
                public void onException(Throwable throwable) {
                    logger.error("<<< [Callback]MQ Event (key="
                            + key + ") Message To MQ On Error", throwable);
                }
            });
        } catch (Throwable th) {
            logger.error("<<< [Outer]MQ Event (key=" + key + ") Message To MQ On Error", th);
        }
    }

}
