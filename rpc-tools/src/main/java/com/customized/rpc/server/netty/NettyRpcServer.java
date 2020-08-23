package com.customized.rpc.server.netty;

import com.customized.rpc.core.executors.ExecutorsPool;
import com.customized.rpc.server.RpcServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NettyRpcServer implements RpcServer {


    private static Logger log = LoggerFactory.getLogger(NettyRpcServer.class);

    private int port = 9000;

    private volatile boolean shutdown = false;

    public NettyRpcServer() {

    }

    public NettyRpcServer(int port) {
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

    }

    @Override
    @PreDestroy
    public void stop() {
        this.shutdown = true;
        log.warn("==> BioRpcServer Shutdown Now..");
    }
}
