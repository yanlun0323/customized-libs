package com.proxy.smart.core.socket;


import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by cjl on 2015/9/9.
 */
public class SocketProxy extends Thread {

    private ServerSocket server;

    public SocketProxy(ServerSocket _server) {
        server = _server;
    }

    @Override
    public void run() { // 线程运行函数
        Socket connection;
        while (true) {
            try {
                connection = server.accept();
                new SocketServerThread(connection).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}