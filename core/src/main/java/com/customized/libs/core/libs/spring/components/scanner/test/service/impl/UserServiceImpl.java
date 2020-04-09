package com.customized.libs.core.libs.spring.components.scanner.test.service.impl;

import com.customized.libs.core.libs.spring.components.scanner.MyComponent;
import com.customized.libs.core.libs.spring.components.scanner.test.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yan
 */
@MyComponent
@Slf4j
public class UserServiceImpl implements UserService {

    @Override
    public void hello(String data) {
        log.warn("==> {}", data);
    }
}
