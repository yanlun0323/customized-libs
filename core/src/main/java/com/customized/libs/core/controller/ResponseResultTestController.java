package com.customized.libs.core.controller;

import com.customized.libs.core.annotations.ResponseResult;
import com.customized.libs.core.libs.rpc.test.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yan
 */
@RestController
@RequestMapping("handler")
public class ResponseResultTestController {

    @GetMapping("getUser")
    @ResponseResult
    public User getUser() {
        User user = new User();
        user.setAge(10);
        user.setUsername("JavaCoder");
        return user;
    }
}
