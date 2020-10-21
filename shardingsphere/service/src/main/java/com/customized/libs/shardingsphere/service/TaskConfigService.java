package com.customized.libs.shardingsphere.service;

import com.alibaba.fastjson.JSON;
import com.customized.libs.shardingsphere.dao.generate.TSpringScheduledCronMapper;
import com.customized.libs.shardingsphere.entity.generate.TSpringScheduledCron;
import com.customized.libs.shardingsphere.entity.generate.TSpringScheduledCronExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author yan
 */
@Service
public class TaskConfigService {

    private static final Logger log = LoggerFactory.getLogger(TaskConfigService.class);

    @Autowired
    private TSpringScheduledCronMapper tSpringScheduledCronMapper;

    public List<TSpringScheduledCron> findAll() {
        List<TSpringScheduledCron> records = this.tSpringScheduledCronMapper
                .selectByExample(new TSpringScheduledCronExample());

        log.info("==> Spring Task({})", JSON.toJSONString(records, true));

        return records;
    }

    public TSpringScheduledCron findByCronKey(String name) {
        TSpringScheduledCronExample example = new TSpringScheduledCronExample();
        example.createCriteria().andCronKeyEqualTo(name);

        return CollectionUtils.firstElement(this.tSpringScheduledCronMapper
                .selectByExample(example));
    }
}
