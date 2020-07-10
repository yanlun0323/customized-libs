package com.proxy.smart.starter;

import com.proxy.smart.core.http.HttpProxy;
import com.proxy.smart.core.socket.SocketProxy;

import java.io.IOException;
import java.net.ServerSocket;

public class ProxyStarter {

    public static void main(String[] args) {
        try {
            ServerSocket httpserver = new ServerSocket(8808);
            // 建立 HTTP 侦听套接字
            System.out.println("HTTP Proxy started on " + httpserver.getLocalPort());

            ServerSocket socksserver = new ServerSocket(8818);
            // 建立 SOCKS 侦听套接字
            System.out.println("SOCKS Proxy started on " + socksserver.getLocalPort());

            // 建立HTTP 侦听线程
            new HttpProxy(httpserver).start();
            // 建立 SOCKS 侦听线程
            new SocketProxy(socksserver).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
