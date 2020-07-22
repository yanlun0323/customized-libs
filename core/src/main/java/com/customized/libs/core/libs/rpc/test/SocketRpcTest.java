package com.customized.libs.core.libs.rpc.test;

import com.customized.libs.core.libs.rpc.RpcClient;
import com.customized.libs.core.libs.rpc.RpcServer;
import com.customized.libs.core.libs.rpc.socket.base.SocketRpcClientImpl;
import com.customized.libs.core.libs.rpc.socket.base.SocketRpcServerImpl;

import java.net.InetSocketAddress;

public class SocketRpcTest {

    @SuppressWarnings("all")
    public static void main(String[] args) throws InterruptedException {
        int port = 9986;
        new Thread(() -> {
            RpcServer rpcServer = new SocketRpcServerImpl();
            rpcServer.export(Tinterface.class, TinterfaceImpl.class);
            rpcServer.open(port);
        }).start();

        // 休眠-确保Server成功开启监听服务
        Thread.sleep(5000);

        RpcClient client = new SocketRpcClientImpl();
        client.connect(new InetSocketAddress("127.0.0.1", port));

        Tinterface tinterface = (Tinterface) client.getInvoker(Tinterface.class);
        System.out.println(tinterface.send("rpc 测试用例0"));
        System.out.println(tinterface.send("rpc 测试用例1"));
        System.out.println(tinterface.send("rpc 测试用例2"));
        System.out.println(tinterface.send("rpc 测试用例3"));
    }
}
