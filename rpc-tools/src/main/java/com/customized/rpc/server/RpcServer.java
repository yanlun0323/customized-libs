package com.customized.rpc.server;

/**
 * RPC服务端接口定义（可用BIO/NIO/AIO/NETTY实现）
 */
public interface RpcServer {

    /**
     * 启动服务
     */
    void start();

    /**
     * 停止服务
     */
    void stop();
}
