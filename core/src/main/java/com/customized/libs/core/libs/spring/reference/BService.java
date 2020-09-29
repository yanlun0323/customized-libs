package com.customized.libs.core.libs.spring.reference;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class BService implements InitializingBean {

    public void doSomething() {
        System.out.println("BService Do Something!!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[ERROR] BService Instance Created!!");
    }
}
