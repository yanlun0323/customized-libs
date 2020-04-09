package com.customized.libs.core.libs.spring.components.scanner.test.service.impl;

import com.customized.libs.core.libs.spring.components.scanner.MyComponent;
import com.customized.libs.core.libs.spring.components.scanner.test.service.UserSessionService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yan
 */
@MyComponent
@Slf4j
public class UserSessionServiceImpl implements UserSessionService {

    @Override
    public void getUserSession() {
        log.warn("==> getUserSession Success!!");
    }
}
