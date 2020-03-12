package com.customized.libs.core.libs.spring.condition;

import lombok.Data;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Data
@Order(0)
public class AmazonProperties {

    private String associateId;

}
