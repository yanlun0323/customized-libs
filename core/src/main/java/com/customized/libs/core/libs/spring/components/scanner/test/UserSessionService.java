package com.customized.libs.core.libs.spring.components.scanner.test;

import com.customized.libs.core.libs.spring.components.scanner.MyComponent;
import lombok.extern.slf4j.Slf4j;

@MyComponent
@Slf4j
public class UserSessionService {

    public void getUserSession() {
        log.warn("==> getUserSession Success!!");
    }
}
