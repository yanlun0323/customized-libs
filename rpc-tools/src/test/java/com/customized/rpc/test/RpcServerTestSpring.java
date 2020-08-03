package com.customized.rpc.test;

import com.customized.rpc.core.configuration.RpcServerConfig;
import com.customized.rpc.test.config.RpcServerTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RpcServerConfig.class, RpcServerTestConfig.class})
public class RpcServerTestSpring {


    @Test
    public void test01() throws InterruptedException {
        // Spring会自动注册，只要保证容器存活
        Thread.sleep(Integer.MAX_VALUE);
    }
}
