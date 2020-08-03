package com.customized.rpc.server.bio;

import com.customized.rpc.core.executors.ExecutorsPool;
import com.customized.rpc.core.protocol.RpcRequest;
import com.customized.rpc.server.RequestHandler;
import com.customized.rpc.server.RpcServer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BioRpcServer implements RpcServer {

    private static Logger log = LoggerFactory.getLogger(BioRpcServer.class);

    private int port = 9000;

    private volatile boolean shutdown = false;

    public BioRpcServer() {

    }

    public BioRpcServer(int port) {
        this.port = port;
    }

    /**
     * start()方法是堵塞的，所以不能在init()方法中直接调用，否则会导致Spring线程堵塞，所以通过异步的方式执行
     */
    @PostConstruct
    public void init() {
        ExecutorsPool.FIXED_EXECUTORS.submit(this::start);
    }

    @Override
    public void start() {
        try {
            ServerSocket server = new ServerSocket(this.port);

            log.info("服务启动成功，端口：{}", this.port);

            while (!this.shutdown) {
                // 接受客户端请求
                Socket client = server.accept();
                ExecutorsPool.FIXED_EXECUTORS.submit(() -> {
                    // 使用JDK的序列化流
                    try (
                            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())
                    ) {
                        // 获取请求参数
                        RpcRequest request = (RpcRequest) in.readObject();
                        log.info("Request Service ==> {}.{}({})",
                                request.getClassName(),
                                request.getMethodName(),
                                StringUtils.join(request.getParameterTypes(), ", ")
                        );

                        log.info("Request Params ==> {}", StringUtils.join(request.getParameters(), ", "));

                        // 处理请求
                        out.writeObject(RequestHandler.handleRequest(request));
                    } catch (IOException | ClassNotFoundException e) {
                        log.error("==> BioRpcServer Client Connect Failed..", e);
                        throw new RuntimeException(e);
                    }
                });
            }

        } catch (IOException e) {
            log.error("==> BioRpcServer Start Failed..", e);
        }
    }

    @Override
    @PreDestroy
    public void stop() {
        this.shutdown = true;
        log.warn("==> BioRpcServer Shutdown Now..");
    }
}
