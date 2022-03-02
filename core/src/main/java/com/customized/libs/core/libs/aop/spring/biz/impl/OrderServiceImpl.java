package com.customized.libs.core.libs.aop.spring.biz.impl;

import com.customized.libs.core.libs.aop.spring.biz.OrderService;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/2/28 16:05
 */
public class OrderServiceImpl implements OrderService {

    @Override
    public String queryOrder(String orderId) {
        return String.format("%s:%s", orderId, "Query!");
    }
}
