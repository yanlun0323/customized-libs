package com.customized.rpc.client.bio;

import com.customized.rpc.client.RpcClient;
import com.customized.rpc.core.protocol.RpcRequest;
import com.customized.rpc.core.protocol.RpcResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class BioRpcClient implements RpcClient {

    private static Logger log = LoggerFactory.getLogger(BioRpcClient.class);

    private String host;

    private int port;

    public BioRpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * TCP短连接通讯
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public RpcResponse sendRequest(RpcRequest request) throws Exception {
        try (
                Socket socket = new Socket(host, port);

                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            log.info("==> RpcClient Connect Success({}:{})!!", host, port);

            // 发送请求
            out.writeObject(request);
            log.info("==> Send Request, Target Host{}:{}, Service: {}.{}({})", host, port,
                    request.getClassName(), request.getMethodName(),
                    StringUtils.join(request.getParameterTypes(), ", ")
            );

            return (RpcResponse) in.readObject();
        }
    }
}
