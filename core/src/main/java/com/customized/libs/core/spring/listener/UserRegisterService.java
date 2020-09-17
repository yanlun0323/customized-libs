package com.customized.libs.core.spring.listener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserRegisterService implements ApplicationContextAware {

    private ApplicationContext context;

    @PostConstruct
    public void userRegister() {
        UserRegister source = new UserRegister();
        source.setPassword("123456");
        source.setUsername("coder");
        UserRegisterEvent event = new UserRegisterEvent(source);


        context.publishEvent(event);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
