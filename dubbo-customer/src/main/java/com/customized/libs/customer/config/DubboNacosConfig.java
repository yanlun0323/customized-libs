/*
* Demo for Nacos
* pom.xml
    <dependency>
        <groupId>com.alibaba.nacos</groupId>
        <artifactId>nacos-client</artifactId>
        <version>${version}</version>
    </dependency>
*/
package com.customized.libs.customer.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Config service example
 *
 * @author Nacos
 */
@Configuration
@EnableNacosConfig(globalProperties = @NacosProperties(
        serverAddr = "172.19.80.13:8848", namespace = "dbb536bd-96a3-47e6-b8dd-5e6344602c86"
))
@NacosPropertySource(dataId = "dubbo-provider", autoRefreshed = true)
public class DubboNacosConfig {

    private static Logger logger = LoggerFactory.getLogger(DubboNacosConfig.class);

    public static void main(String[] args) throws NacosException {
        DubboNacosConfig.init();
    }

    /**
     * 这里的namespace实际上是命名空间ID
     *
     * @throws NacosException
     */
    public static void init() throws NacosException {
        String serverAddr = "172.19.80.13:8848";
        String dataId = "dubbo-provider";
        String group = "DEFAULT_GROUP";

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);

        properties.put(PropertyKeyConst.NAMESPACE, "dbb536bd-96a3-47e6-b8dd-5e6344602c86");
        ConfigService configService = NacosFactory.createConfigService(properties);

        String content = configService.getConfig(dataId, group, 5000);
        logger.warn("<<< Dubbo-Customer Nacos Config ==> \r\n{}", content);

        configService.addListener(dataId, group, new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                logger.warn("<<< Dubbo-Customer Nacos Receive Data ==> \r\n{}", configInfo);
            }

            @Override
            public Executor getExecutor() {
                return null;
            }
        });
    }
}
