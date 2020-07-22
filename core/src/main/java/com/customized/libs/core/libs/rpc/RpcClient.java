package com.customized.libs.core.libs.rpc;

import java.net.InetSocketAddress;

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
