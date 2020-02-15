package com.customized.libs.libs.dubbo.spi;

import org.springframework.stereotype.Component;

/**
 * 先前遇到过一个问题，是因为POM打包的时候未指定包含的META-INF下的文件打包，导致SPI无法生效
 * 如下配置即可：
 * <include>META-INF/**</include>
 *
 * @author yan
 */
@Component
public class UserService {

    public void init() {
        System.out.println("UserService ==> Init");
    }
}
