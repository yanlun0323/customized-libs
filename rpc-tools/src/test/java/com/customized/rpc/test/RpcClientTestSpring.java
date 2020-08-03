package com.customized.rpc.test;

import com.customized.rpc.core.annotations.Reference;
import com.customized.rpc.test.config.RpcClientTestConfig;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RpcClientTestConfig.class})
public class RpcClientTestSpring {

    @Reference
    private HelloService helloService;

    @Test
    public void test01() {
        String rt = this.helloService.sayHello("Jack");
        System.out.println(rt);

        Assertions.assertEquals("hello, Jack", rt, "调用失败");
    }
}
