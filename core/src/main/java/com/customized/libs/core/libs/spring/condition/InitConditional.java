package com.customized.libs.core.libs.spring.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author yan
 */
public class InitConditional implements Condition {

    /**
     * 若不包含则初始化Bean
     *
     * @param conditionContext
     * @param annotatedTypeMetadata
     * @return
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //判断是否已经包含了amazonTest Bean
        return !conditionContext.getBeanFactory().containsBean("amazonProperties");
    }
}