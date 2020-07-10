package com.proxy.smart.core.http;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by cjl on 2015/9/8.
 */
public class HttpChannel extends Thread{
    private DataInputStream in;
    private DataOutputStream out;
    public HttpChannel(DataInputStream sin, DataOutputStream cout) {
        in = sin;
        out = cout;
        start();
    }

    @Override
    public void run() {
        int len = 0;
        byte buf[] = new byte[10240];
        while (true) {
            try {
                if (len == -1) {
                    break;
                }
                if (len > 0) {
                    System.out.println("post<<"+new String(buf));
                    out.write(buf, 0, len);
                    out.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}