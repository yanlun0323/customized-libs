package com.customized.libs.core.components;

import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 动态规则扩展 https://github.com/alibaba/Sentinel/wiki/%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%99%E6%89%A9%E5%B1%95
 *
 * @author yan
 */
@Component
public class SentinelRuleConfig {

    @PostConstruct
    public void init() {
        List<FlowRule> rules = new ArrayList<>();
        rules.add(SentinelRules.DUBBO_INVOKER_RULE);
        rules.add(SentinelRules.DUBBO_INVOKER_NOARGS_RULE);
        rules.add(SentinelRules.DUBBO_INVOKER_EMPTY_RULE);
        FlowRuleManager.loadRules(rules);
    }
}
