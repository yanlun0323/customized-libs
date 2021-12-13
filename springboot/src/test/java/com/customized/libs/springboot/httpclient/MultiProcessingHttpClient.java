package com.customized.libs.springboot.httpclient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class MultiProcessingHttpClient {

    public static void main(String[] args) throws InterruptedException {

        HttpClientProvider httpClientProvider = new HttpClientProvider();

        multiRequest(httpClientProvider, "http://localhost:8803/httpclient/multi1");
        multiRequest(httpClientProvider, "http://localhost:8803/httpclient/multi2");

        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void multiRequest(HttpClientProvider httpClientProvider, String s) {
        for (int i = 0; i < 15; i++) { // MaxPerRoute若设置为2，则5线程分3组返回（2、2、1），共15秒
            int finalI = i;
            new Thread(() -> {
                try {
                    HttpGet get = httpClientProvider.getHttpGet(s);
                    CloseableHttpResponse execute = httpClientProvider.getHttpClient().execute(get);
                    System.out.printf("Url(%s) %s Response ==> " + EntityUtils.toString(execute.getEntity()), s, (finalI + 1));
                    System.out.println();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
