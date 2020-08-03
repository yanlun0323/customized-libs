package com.customized.rpc.client;

import com.customized.rpc.core.protocol.RpcRequest;
import com.customized.rpc.core.protocol.RpcResponse;

/**
 * RPC客户端
 */
public interface RpcClient {

    /**
     * 发送RPC请求，获取响应
     *
     * @param request
     * @return
     */
    RpcResponse sendRequest(RpcRequest request) throws Exception;
}
