package com.smart.lib.spring.ioc.bean.io;

import com.smart.lib.spring.ioc.bean.utils.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 通过 HTTP 的方式读取云服务的文件，我们也可以把配置文件放到 GitHub 或者 Gitee 上。
 *
 * @author yan
 * @version 1.0
 * @description 基于网络请求的资源读取
 * @date 2022/8/16 09:54
 */
public class UrlResource implements Resource {

    private final URL url;

    public UrlResource(String url) throws MalformedURLException {
        Assert.notNull(url, "Url must not be null");
        this.url = new URL(url);
    }

    public UrlResource(URL url) {
        Assert.notNull(url, "URL must not be null");
        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection connection = this.url.openConnection();
        try {
            return connection.getInputStream();
        } catch (Exception ex) {
            if (connection instanceof HttpURLConnection) {
                ((HttpURLConnection) connection).disconnect();
            }
            throw ex;
        }
    }
}
