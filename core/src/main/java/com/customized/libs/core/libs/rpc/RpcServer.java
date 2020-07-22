package com.customized.libs.core.libs.rpc;

public interface RpcServer {

    /**
     * Service注册（接口 + 实现类）
     *
     * @param service
     * @param impl
     */
    void export(Class service, Class impl);

    /**
     * 打开监听端口
     *
     * @param port
     */
    void open(int port);
}
