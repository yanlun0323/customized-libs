package com.customized.libs.core.libs.spring.components.scanner.test;

import com.customized.libs.core.libs.spring.components.scanner.MyComponent;
import lombok.extern.slf4j.Slf4j;

@MyComponent
@Slf4j
public class UserService {

    public void hello(String data) {
        log.warn("==> {}", data);
    }
}
