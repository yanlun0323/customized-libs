package com.smart.lib.spring.ioc.bean.io;

import com.smart.lib.spring.ioc.bean.utils.Assert;
import com.smart.lib.spring.ioc.bean.utils.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 这一部分的实现是用于通过 ClassLoader 读取ClassPath 下的文件信息，具体的读取过程主要是：classLoader.getResourceAsStream(path)
 *
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/16 09:39
 */
public class ClassPathResource implements Resource {

    private final String path;

    private final ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        Assert.notNull(path, "Path must be not null");
        this.path = path;
        this.classLoader = classLoader == null ? ClassUtils.getDefaultClassLoader() : classLoader;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream resourceAsStream = classLoader.getResourceAsStream(path);
        if (resourceAsStream == null) {
            throw new FileNotFoundException(
                    this.path + " cannot be opened because it does not exist");
        }
        return resourceAsStream;
    }
}
