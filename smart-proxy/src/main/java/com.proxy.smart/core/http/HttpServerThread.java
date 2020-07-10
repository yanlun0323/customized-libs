package com.proxy.smart.core.http;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cjl on 2015/9/8.
 */
public class HttpServerThread implements Runnable {

    private Socket client;

    private static Map<String, String> filter = new HashMap<>(64);

    public HttpServerThread(Socket _connection) {
        client = _connection;
    }

    /**
     * 替换数据 - 篡改数据
     *
     * @param buf
     */
    private byte[] replease(byte[] buf) {
        String tmp = new String(buf);
        for (String key : filter.keySet()) {
            if (tmp.contains(key)) {
                tmp = tmp.replace(key, filter.get(key));
            }
        }
        return tmp.getBytes();
    }

    public static void put(String str, String rep) {
        filter.put(str, rep);
    }


    @Override
    public void run() { // 线程运行函数
        byte buf[] = new byte[2048], buf1[] = new byte[2048], buf2[] = new byte[2048];
        int creadlen = 0, sreadlen = 0;
        int i;
        String s = null, s1 = null, s2 = null;
        Socket server = null;
        int port = 80;
        DataInputStream cin = null, //客户端输入流
                sin = null; //服务端输入流
        DataOutputStream cout = null, //客户端输出流
                sout = null;  //服务端输出流
        int method = 0;
        try {
            cin = new DataInputStream(client.getInputStream());
            cout = new DataOutputStream(client.getOutputStream());
            if (cin != null && cout != null) {
                creadlen = cin.read(buf, 0, 2048); // 从客户端读数据
                if (creadlen > 0) { // 读到数据
                    s = new String(buf);
                    if (s.indexOf("\r\n") != -1)
                        s = s.substring(0, s.indexOf("\r\n"));
                    if (s.indexOf("GET ") != -1)
                        method = 0;// 如读到 GET 请求
                    if (s.indexOf("CONNECT ") != -1) {
                        // 读到 CONNECT 请求 , 返回 HTTP 应答
                        s1 = s.substring(s.indexOf("CONNECT ") + 8, s.indexOf
                                ("HTTP/"));
                        s2 = s1;
                        s1 = s1.substring(0, s1.indexOf(":"));
                        s2 = s2.substring(s2.indexOf(":") + 1);
                        s2 = s2.substring(0, s2.indexOf(" "));
                        port = Integer.parseInt(s2);
                        method = 1;
                        sendAck(cout);
                    }
                    if (s.indexOf("POST ") != -1)// 如读到 POST 请求
                        method = 2;

                    if (s.indexOf("http://") != -1 && s.indexOf("HTTP/") != -1) {
                        // 从所读数据中取域名和端口号
                        s1 = s.substring(s.indexOf("http://") + 7, s.indexOf("HTTP/"));
                        s1 = s1.substring(0, s1.indexOf("/"));
                        if (s1.indexOf(":") != -1) {
                            s2 = s1;
                            s1 = s1.substring(0, s1.indexOf(":"));
                            s2 = s2.substring(s2.indexOf(":") + 1);
                            port = Integer.parseInt(s2);
                            method = 0;
                        }
                    }
                    if (s1 != null) {
                        server = new Socket(s1, port);
                        // 根据读到的域名和端口号建立套接字
                        sin = new DataInputStream(server.getInputStream());
                        sout = new DataOutputStream(server.getOutputStream());
                        if (sin != null && sout != null && server != null) {
                            if (method == 0) {
                                sreadlen = doGet(buf, creadlen, sin, cout, sout);
                            }
                            if (method == 1) { // 如读到 CONNECT 请求
                                sreadlen = doConnect(buf, sreadlen, cin, sin, cout, sout);
                            }
                            if (method == 2) { // 如读到 POST 请求
                                // 向外网发送 POST 请求
                                doPost(buf, creadlen, sreadlen, cin, sin, cout, sout);
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

    /**
     * 发生确认
     *
     * @param cout 输出流
     * @throws IOException
     */
    private void sendAck(DataOutputStream cout) throws IOException {
        String s2;
        byte[] buf2;
        s2 = "HTTP/1.0 200 Connection established\r\n";
        s2 = s2 + "Proxy-agent: proxy\r\n\r\n";
        buf2 = s2.getBytes();
        cout.write(buf2);
        cout.flush();
    }

    /**
     * 处理POST 请求
     *
     * @param buf      数据缓存区
     * @param creadlen 客户端读取的buf的长度
     * @param sreadlen 服务端读取的buf的长度
     * @param cin      客户端输入流
     * @param sin      服务端输入流
     * @param cout     客户端输出流
     * @param sout     服务端输出流
     * @throws IOException
     */
    private void doPost(byte[] buf, int creadlen, int sreadlen, DataInputStream cin, DataInputStream sin, DataOutputStream cout, DataOutputStream sout) throws IOException {
        write(buf, creadlen, sout);
        // 建立线程 , 用于从外网读数据 , 并返回给内网客户端
        HttpChannel thread1 = new HttpChannel(sin, cout);
        while ((sreadlen = cin.read(buf, 0, 2048)) != -1) { // 循环
            try {
                System.out.println("post>>" + new String(buf));
                if (sreadlen > 0) { // 读到数据 , 则发送给外网
                    write(buf, sreadlen, sout);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                break;
            }
        }
    }

    /**
     * 写数据
     *
     * @param buf      缓冲区
     * @param creadlen 读取的偏移量
     * @param sout     输出流
     * @throws IOException
     */
    private void write(byte[] buf, int creadlen, DataOutputStream sout) throws IOException {
        sout.write(buf, 0, creadlen);
        sout.flush();
    }

    /**
     * 处理HTTPconnect  待定
     *
     * @param buf      缓冲区
     * @param sreadlen 服务端读取的buf的长度
     * @param cin      客户端输入流
     * @param sin      服务端输入流
     * @param cout     客户端输出流
     * @param sout     服务端输出流
     * @return
     */
    private int doConnect(byte[] buf, int sreadlen, DataInputStream cin, DataInputStream sin, DataOutputStream cout, DataOutputStream sout) {
        // 建立线程 , 用于从外网读数据 , 并返回给内网客户端
        HttpChannel thread1 = new HttpChannel(sin, cout);
        try {
            while ((sreadlen = cin.read(buf, 0, 2048)) != -1) { // 循环
                // 从内网读数据
                if (sreadlen > 0) {
                    // 读到数据 , 则发送给外网
                    System.out.println("CONN>>" + new String(buf));
                    write(buf, sreadlen, sout);
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return sreadlen;
    }

    /**
     * 处理GET请求
     *
     * @param buf      缓冲区
     * @param creadlen 客户端读取buf的长度
     * @param sin      服务端输入流
     * @param cout     客户端输出流
     * @param sout     服务端输出流
     * @return
     * @throws IOException
     */
    private int doGet(byte[] buf, int creadlen, DataInputStream sin, DataOutputStream cout, DataOutputStream sout)
            throws IOException {
        int sreadlen;// 如读到 GET 请求，向外网发出 GET 请求
        System.out.println("[get] >> " + new String(buf));
        write(buf, creadlen, sout);

        try {
            byte[] fileByteBuff = new byte[1024];

            while (true) {
                sreadlen = sin.read(fileByteBuff);
                if (-1 == sreadlen) {
                    break;
                }
                System.out.println("[get] << " + new String(fileByteBuff));
                write(fileByteBuff, sreadlen, cout);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return 0;
    }
}