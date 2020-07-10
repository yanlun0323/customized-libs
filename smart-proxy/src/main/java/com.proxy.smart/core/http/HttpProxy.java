package com.proxy.smart.core.http;


import com.proxy.smart.core.utils.ExecutorsPool;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by cjl on 2015/9/8.
 */
public class HttpProxy extends Thread {

    private ServerSocket server;

    public HttpProxy(ServerSocket _server) {
        server = _server;
    }

    @Override
    public void run() {
        Socket connection;
        while (true) {
            try {
                connection = server.accept();
                //接受到请求，就新建一个处理请求的服务线程，将当前请求传递到线程里面
                Socket finalConnection = connection;
                ExecutorsPool.CORE0_EXECUTORS
                        .submit(new HttpServerThread(finalConnection));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}