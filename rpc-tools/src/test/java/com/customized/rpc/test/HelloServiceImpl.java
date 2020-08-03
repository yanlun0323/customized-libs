package com.customized.rpc.test;

import com.customized.rpc.core.annotations.Service;

@Service(HelloService.class)
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return String.format("hello, %s", name);
    }
}
