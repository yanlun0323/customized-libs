package com.customized.libs.core.libs.spring.condition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * @author yan
 */
@Component
@Conditional(InitConditional.class)
public class AmazonTest {

    @Autowired
    private AmazonProperties amazonProperties;

    public void testGet() {
        System.out.println("    testGet Associated");
        System.out.println(amazonProperties.getAssociateId());
    }
}