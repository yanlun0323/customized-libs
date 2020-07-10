package com.proxy.smart.core.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by cjl on 2015/9/8.
 */
public class SocketServerThread extends Thread {
    private Socket client;

    int bytes2int(byte b) { // 将 byte 类型转换为 int 类型
        int mask = 0xff;
        int temp = 0;
        int res = 0;
        res <<= 8;
        temp = b & mask;
        res |= temp;
        return res;
    }

    public SocketServerThread(Socket _connection) {
// 构造函数
        client = _connection;
    }

    @Override
    public void run() { // 线程运行函数
        byte creadBuf[] = new byte[10000], cwriteBuf[] = new byte[10000], buf2[] = new byte[10000];
        int creadlen = 0, sreadlen = 0, readbytes2 = 0;
        DataInputStream cin = null, sin = null;
        DataOutputStream cout = null, sout = null;
        String s = null, s1 = null, s2 = null;
        int i;
        int port = 0, port1 = 0;
        String ip = null;
        Socket server = null;
        byte ip1[] = new byte[4], ip2[] = new byte[4];
        try {
            cin = new DataInputStream(client.getInputStream());
            cout = new DataOutputStream(client.getOutputStream());
            if (cin != null && cout != null) {
                creadlen = cin.read(creadBuf, 0, 10000); // 从客户端读数据
                if (creadlen > 0) { // 读到数据
                    if (creadBuf[0] == 5) { // 读到 SOCK5 请求
                        // 发送 SOCK5 应答,第一次
                        cwriteBuf[0] = 5;
                        cwriteBuf[1] = 0;
                        cout.write(cwriteBuf, 0, 2);
                        cout.flush();
                        creadlen = cin.read(creadBuf, 0, 10000);
                        // 继续读 SOCK5 请求
                        if (creadlen > 0) { // 读到 SOCK5 请求
                            if (creadBuf[0] == 5 && creadBuf[1] == 1 && creadBuf[2] == 0 && creadBuf[3] == 1) {//TCP 请求
                                // 从该请求中取要连接的 IP 地址和端口号 , 并建立TCP 套接字
                                ip = bytes2int(creadBuf[4]) + "." + bytes2int(creadBuf[5]) + "." + bytes2int(creadBuf[6]) + "." + bytes2int(creadBuf[7]);
                                port = creadBuf[8] * 256 + creadBuf[9];
                                server = new Socket(ip, port);//创建到远程服务端的套接字
                                sin = new DataInputStream(server.getInputStream());
                                sout = new DataOutputStream(server.getOutputStream());
                                // 发送 SOCK5 应答
                                ip1 = server.getLocalAddress().getAddress();
                                port1 = server.getLocalPort();
                                creadBuf[1] = 0;
                                creadBuf[4] = ip1[0];
                                creadBuf[5] = ip1[1];
                                creadBuf[6] = ip1[2];
                                creadBuf[7] = ip1[3];
                                creadBuf[8] = (byte) (port1 >> 8);
                                creadBuf[9] = (byte) (port1 & 0xff);
                                cout.write(creadBuf, 0, 10);//回复应答,第二次
                                cout.flush();
                                // 建立线程 , 用于给客户端返回数据
                                SocketChannel channel = new SocketChannel(sin, cout);
                                while (true) { // 循环读数据
                                    try {
                                        if (sreadlen == -1) break; // 无数据则退出循环
                                        sreadlen = cin.read(cwriteBuf, 0, 10000);
                                        // 从客户端读数据
                                        if (sreadlen > 0) { // 读到数据 , 则发送给外网
                                            sout.write(cwriteBuf, 0, sreadlen);
                                            sout.flush();
                                        }
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (creadBuf[0] == 4) { // 读到 SOCK4 请求
                        port = creadBuf[2] * 256 + creadBuf[3]; // 从请求中取端口号
                        if (creadBuf[4] == 0 && creadBuf[5] == 0 && creadBuf[6] == 0 && creadBuf[7] != 0 && creadBuf[8] == 0) {
                            // 如请求中为域名
                            s = new String(creadBuf);
                            s = s.substring(9);
                            s = s.substring(0, s.indexOf("\0"));
                        } else { // 如请求中为 IP 地址
                            ip = bytes2int(creadBuf[4]) + "." + bytes2int(creadBuf[5]) + "." + bytes2int(creadBuf[6]) + "." + bytes2int(creadBuf[7]);
                            s = ip;
                        }
                        for (i = 1; i <= 9; i++)
                            creadBuf[i - 1] = 0;
                        server = new Socket(s, port);
                        // 根据 SOCK4 请求中的地址建立 TCP 套接字
                        sin = new DataInputStream(server.getInputStream());
                        sout = new DataOutputStream(server.getOutputStream());
                        // 返回 SOCK4 应答,第二次
                        ip1 = server.getLocalAddress().getAddress();
                        port1 = server.getLocalPort();
                        creadBuf[0] = 0;
                        creadBuf[1] = 0x5a;
                        creadBuf[2] = ip1[0];
                        creadBuf[3] = ip1[1];
                        creadBuf[4] = (byte) (port1 >> 8);
                        creadBuf[5] = (byte) (port1 & 0xff);
                        cout.write(creadBuf, 0, 8);
                        cout.flush();
                        // 建立线程 , 用于给客户端返回数据
                        SocketChannel thread1 = new SocketChannel(sin, cout);
                        while (true) { // 循环读数据
                            try {
                                if (sreadlen == -1) break; // 无数据则退出循环
                                sreadlen = cin.read(cwriteBuf, 0, 10000);
                                // 从客户端读数据
                                if (sreadlen > 0) { // 读到数据 , 则发送给外网
                                    sout.write(cwriteBuf, 0, sreadlen);
                                    sout.flush();
                                }
                            } catch (Exception e1) {
                                e1.printStackTrace();
                                break;
                            }
                        }
                    }
                }
            }
            // 执行关闭操作
            if (sin != null) sin.close();
            if (sout != null) sout.close();
            if (server != null) server.close();
            if (cin != null) cin.close();
            if (cout != null) cout.close();
            if (client != null) client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}