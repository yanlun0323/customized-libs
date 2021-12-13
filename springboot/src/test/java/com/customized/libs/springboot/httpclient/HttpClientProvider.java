package com.customized.libs.springboot.httpclient;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Closeable;
import java.util.Map;

@Service
public class HttpClientProvider {

    private CloseableHttpClient httpClient;

    public HttpClientProvider() {
        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            // 全部信任 不做身份鉴定
            builder.loadTrustMaterial(null, (x509Certificates, s) -> true);
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build()
                    , new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", new PlainConnectionSocketFactory())
                    .register("https", sslsf)
                    .build();
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
            connManager.setMaxTotal(30);
            connManager.setDefaultMaxPerRoute(15);
            this.httpClient = HttpClients.custom()
                    .setConnectionManager(connManager).build();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public HttpGet getHttpGet(String url) {
        return getHttpGet(url, null, false);
    }

    public HttpGet getHttpGet(String url, Map<String, Object> params) {
        return getHttpGet(url, params, false);
    }

    public HttpGet getHttpGet(String url, Map<String, Object> params, Boolean uriEncode) {
        String queryStr = UrlParamUtil.createLinkString(params, uriEncode);
        if (!StringUtils.isEmpty(queryStr)) queryStr = "?" + queryStr;
        HttpGet httpGet = new HttpGet(url + queryStr);
        httpGet.setConfig(RequestConfig.custom()
                .setConnectionRequestTimeout(15000)
                .setConnectTimeout(25000)
                .setSocketTimeout(45000).build()
        );
        return httpGet;
    }

    public HttpPost getHttpPost(String url) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(RequestConfig.custom()
                .setConnectionRequestTimeout(5000)
                .setConnectTimeout(15000)
                .setSocketTimeout(45000).build()
        );
        return httpPost;
    }

    public static void close(HttpRequestBase request, Closeable response) {
        if (request != null) {
            try {
                request.releaseConnection();
            } catch (Throwable th) {
            }
        }
        if (response != null) {
            try {
                response.close();
            } catch (Throwable th) {
            }
        }
    }

}
