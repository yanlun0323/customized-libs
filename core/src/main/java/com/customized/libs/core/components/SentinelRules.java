package com.customized.libs.core.components;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;

/**
 * @author yan
 */
public class SentinelRules {

    public static final String DUBBO_INVOKER_RESOURCE = "DubboInvoker-Dispatch";
    public static final FlowRule DUBBO_INVOKER_RULE = buildRule(DUBBO_INVOKER_RESOURCE, RuleConstant.FLOW_GRADE_QPS, 1);

    public static final String DUBBO_INVOKER_NOARGS_RESOURCE = "DubboInvoker-Dispatch-NoArgs";
    public static final FlowRule DUBBO_INVOKER_NOARGS_RULE = buildRule(DUBBO_INVOKER_NOARGS_RESOURCE, RuleConstant.FLOW_GRADE_QPS, 1);

    public static final String DUBBO_INVOKER_EMPTY_RESOURCE = "DubboInvoker-Dispatch-Empty";
    public static final FlowRule DUBBO_INVOKER_EMPTY_RULE = buildRule(DUBBO_INVOKER_EMPTY_RESOURCE, RuleConstant.FLOW_GRADE_QPS, 1);

    private static FlowRule buildRule(String resource, int grade, int count) {
        FlowRule rule = new FlowRule();
        rule.setResource(resource);
        rule.setGrade(grade);
        rule.setCount(count);
        return rule;
    }
}
