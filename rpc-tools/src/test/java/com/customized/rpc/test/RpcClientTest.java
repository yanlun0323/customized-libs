package com.customized.rpc.test;

import com.customized.rpc.client.RpcClient;
import com.customized.rpc.client.RpcProxyFactory;
import com.customized.rpc.client.bio.BioRpcClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RpcClientTest {

    @Test
    public void test01() {
        // 9000端口貌似被idea占用了，一直连接不成功
        RpcClient client = new BioRpcClient("127.0.0.1", 8002);

        // 通过代理工厂，获取服务
        HelloService helloService = new RpcProxyFactory<>(client, HelloService.class).getProxyObject();
        String rt = helloService.sayHello("Jack");
        System.out.println(rt);

        Assertions.assertEquals("hello, Jack", rt, "调用失败");
    }
}
