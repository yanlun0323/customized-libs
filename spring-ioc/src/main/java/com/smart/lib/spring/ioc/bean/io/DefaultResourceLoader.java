package com.smart.lib.spring.ioc.bean.io;

import com.smart.lib.spring.ioc.bean.utils.Assert;

import java.net.MalformedURLException;

/**
 * @author yan
 * @version 1.0
 * @description 默认资源加载器
 * @date 2022/8/16 10:00
 */
public class DefaultResourceLoader implements ResourceLoader {

    @Override
    public Resource getResource(String location) {
        Assert.notNull(location, "Location must not be null");
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            // 此处实际要去掉前缀，因为文件系统是识别不了classpath:标示前缀的
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        } else {
            try {
                return new UrlResource(location);
            } catch (MalformedURLException e) {
                return new FileSystemResource(location);
            }
        }
    }
}
