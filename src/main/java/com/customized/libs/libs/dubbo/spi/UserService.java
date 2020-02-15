package com.customized.libs.libs.dubbo.spi;

import org.springframework.stereotype.Component;

/**
 * @author yan
 */
@Component
public class UserService {

    public void init() {
        System.out.println("UserService ==> Init");
    }
}
