package com.smart.lib.spring.ioc.bean.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author yan
 * @version 1.0
 * @description 资源读取
 * @date 2022/8/16 09:37
 */
public interface Resource {

    InputStream getInputStream() throws IOException;
}
