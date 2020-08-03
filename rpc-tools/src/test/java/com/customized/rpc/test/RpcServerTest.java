package com.customized.rpc.test;

import com.customized.rpc.server.ServiceRegistry;
import com.customized.rpc.server.bio.BioRpcServer;
import org.junit.jupiter.api.Test;

public class RpcServerTest {


    @Test
    public void test01() {
        ServiceRegistry.doRegister(HelloService.class, HelloServiceImpl.class);
        new BioRpcServer(8002).start();
    }
}
