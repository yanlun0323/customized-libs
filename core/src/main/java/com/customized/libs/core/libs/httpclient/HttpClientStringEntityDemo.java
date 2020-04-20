package com.customized.libs.core.libs.httpclient;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yan
 */
public class HttpClientStringEntityDemo {

    public static void main(String[] args) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost();
        post.setURI(URI.
                create("http://113.247.250.238:8082/touchpay.trade.generateNfcTagData/1.0/bf_touchpay/bf_touchpay_app/45d3d9c5fe564b0897e5809841f06127/ef76748158b32e0336392a8bc082d2329373e576a2a1609696f3221b76be0673/HMAC/HmacSHA256/1586756502099"));
        // buildStringEntity(post);

        buildNvps(post);
        CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(post);
        String data = EntityUtils.toString(response.getEntity());

        System.out.println(data);
    }

    private static void buildStringEntity(HttpPost post) throws UnsupportedEncodingException {
        post.setEntity(new StringEntity("bizContent={\"username\":\"JAVA\"}"));
        post.addHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded"));
    }

    private static void buildNvps(HttpPost post) throws UnsupportedEncodingException {
        List<NameValuePair> nvp = new ArrayList<>();
        nvp.add(new BasicNameValuePair("bizContent", "{\"username\":\"JAVA\"}"));
        post.setEntity(new UrlEncodedFormEntity(nvp));
    }
}
