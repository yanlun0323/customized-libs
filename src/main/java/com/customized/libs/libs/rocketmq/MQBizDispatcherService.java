package com.customized.libs.libs.rocketmq;

import com.customized.libs.libs.rocketmq.producer.CustomizedLibsProducer;
import com.customized.libs.libs.utils.MapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author yan
 */
@Service
public class MQBizDispatcherService {

    @Autowired
    private CustomizedLibsProducer producer;

    @PostConstruct
    public void init() {
        Map<String, Object> data = MapBuilder.
                create("status", "Server load success!!").get();
        this.producer.sendEvent(MQEvent.build("Init", data));
    }
}
