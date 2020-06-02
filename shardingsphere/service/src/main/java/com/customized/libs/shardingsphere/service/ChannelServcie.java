package com.customized.libs.shardingsphere.service;

import com.customized.libs.shardingsphere.dao.generate.TRouteChannelMapper;
import com.customized.libs.shardingsphere.entity.generate.TRouteChannel;
import com.customized.libs.shardingsphere.entity.generate.TRouteChannelExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ChannelServcie {

    private static Logger logger = LoggerFactory.getLogger(ChannelServcie.class);

    @Autowired
    private TRouteChannelMapper tRouteChannelMapper;

    @PostConstruct
    public void setup() {
        List<TRouteChannel> records = this.tRouteChannelMapper.selectByExample(new TRouteChannelExample());
        logger.warn("<[ Route Channel Records ==> {}", records);
    }
}
