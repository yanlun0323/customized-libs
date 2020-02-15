package com.customized.libs.libs.dispatcher.impl;


import com.customized.libs.libs.dispatcher.Register;
import com.customized.libs.libs.dubbo.spi.UserService;

/**
 * @author yan
 */
public class UserRegister implements Register {

    /**
     * 这里依赖于Dubbo的SPI机制内部的IOC容器注入，实际上也是通过SpringContext获取Bean
     */
    private UserService userService;

    @Override
    public void doRegister() {
        this.userService.init();
        System.out.println("UserRegister ==> doRegister");
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
