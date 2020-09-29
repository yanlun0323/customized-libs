package com.customized.libs.core.libs.spring.reference;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AService implements InitializingBean {

    @Autowired
    private BService bService;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[ERROR] AService Instance Created!!");
        this.bService.doSomething();
    }
}
