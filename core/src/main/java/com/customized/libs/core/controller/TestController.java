package com.customized.libs.core.controller;

import com.alipay.sofa.tracer.plugins.httpclient.SofaTracerHttpClientBuilder;
import com.customized.multiple.versions.adapter.annotations.Version;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

/**
 * @author yan
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);


    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 100;

    private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 5;

    private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = (60 * 1000);

    @PostMapping
    public Object test(@RequestBody Map requestBody) {
        LOGGER.info("requestBody={}", requestBody);
        return requestBody;
    }

    @Version("version>=1.2.5 && version <=1.2.7")
    @RequestMapping(value = "bingo", method = RequestMethod.GET)
    public Object testV1() throws IOException {
        testHttpClient();
        return getMessageResp(">=1.2.5 && <=1.2.7");
    }

    @Version("version<1.2.5")
    @RequestMapping(value = "bingo", method = RequestMethod.GET)
    public Object testV2() {
        return getMessageResp("<1.2.5");
    }

    @Version("version>1.2.7")
    @RequestMapping(value = "bingo", method = RequestMethod.GET)
    public Object testV3() throws IOException {
        testHttpClient();
        return getMessageResp(">1.2.7");
    }

    private void testHttpClient() throws IOException {
        CloseableHttpResponse closeableHttpResponse = null;

        try {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            //SOFATracer
            SofaTracerHttpClientBuilder.clientBuilder(httpClientBuilder);

            Registry<ConnectionSocketFactory> schemeRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", SSLConnectionSocketFactory.getSocketFactory())
                    .build();
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(schemeRegistry);
            connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
            connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
            CloseableHttpClient httpClient = httpClientBuilder.setConnectionManager(connectionManager).disableAutomaticRetries().build();

            HttpGet httpGet = new HttpGet(URI.create("http://localhost:9900/test/bingo?version=1.2.0"));
            closeableHttpResponse = httpClient.execute(httpGet);
            LOGGER.info("==> {}", EntityUtils.toString(closeableHttpResponse.getEntity()));
        } finally {
            if (closeableHttpResponse != null) closeableHttpResponse.close();
        }
    }
}