package com.customized.libs.core.libs.aop.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/2/23 12:18
 */
@Service
public class SimpleUserServiceImplInvokerImpl {

    @Autowired
    private SimpleUserServiceImpl userService;

    @Scheduled(fixedDelay = 2000)
    public void test() {
        this.userService.hello();
    }
}
