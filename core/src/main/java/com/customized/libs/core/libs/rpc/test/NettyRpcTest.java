package com.customized.libs.core.libs.rpc.test;

import com.customized.libs.core.libs.rpc.RpcClient;
import com.customized.libs.core.libs.rpc.RpcServer;
import com.customized.libs.core.libs.rpc.netty.base.NettyRpcClientImpl;
import com.customized.libs.core.libs.rpc.netty.base.NettyRpcServerImpl;

import java.net.InetSocketAddress;
import java.util.stream.IntStream;

public class NettyRpcTest {

    @SuppressWarnings("all")
    public static void main(String[] args) throws InterruptedException {
        int port = 9986;
        new Thread(() -> {
            RpcServer rpcServer = new NettyRpcServerImpl();
            rpcServer.export(Tinterface.class, TinterfaceImpl.class);
            rpcServer.open(port);
        }).start();

        // 休眠-确保Server成功开启监听服务
        Thread.sleep(5000);

        RpcClient client = new NettyRpcClientImpl();
        client.connect(new InetSocketAddress("127.0.0.1", port));

        Tinterface tinterface = (Tinterface) client.getInvoker(Tinterface.class);

        IntStream.range(0, 5).forEach(c -> {
            System.err.println(tinterface.send("Rpc ==> Data " + c));
            User user = new User();
            user.setUsername("Rpc");
            user.setAge(c);
            System.err.println(tinterface.send(user, c));
        });
    }
}
