package com.customized.libs.expand.components;

import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
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
