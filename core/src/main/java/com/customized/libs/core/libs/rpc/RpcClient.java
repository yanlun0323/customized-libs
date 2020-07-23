package com.customized.libs.core.libs.rpc;

import java.net.InetSocketAddress;


/**
 * RPC(Remote Procedure Call)远程过程调用，简单的理解是一个节点请求另一个节点提供的服务。它的工作流程是这样的：
 * <p>
 * 1. 执行客户端调用语句，传送参数
 * <p>
 * 2. 调用本地系统发送网络消息
 * <p>
 * 3. 消息传送到远程主机
 * <p>
 * 4. 服务器得到消息并取得参数
 * <p>
 * 5. 根据调用请求以及参数执行远程过程（服务）
 * <p>
 * 6. 执行过程完毕，将结果返回服务器句柄
 * <p>
 * 7. 服务器句柄返回结果，调用远程主机的系统网络服务发送结果
 * <p>
 * 8. 消息传回本地主机
 * <p>
 * 9. 客户端句柄由本地主机的网络服务接收消息
 * <p>
 * 10. 客户端接收到调用语句返回的结果数据
 *
 * @author yan
 */
public interface RpcClient<T> {

    /**
     * 连接服务端
     *
     * @param socketAddress
     */
    void connect(InetSocketAddress socketAddress);


    /**
     * 获取Invoker
     *
     * @param service
     * @param <T>
     * @return
     */
    <T> T getInvoker(final Class<T> service);
}
