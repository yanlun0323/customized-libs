package com.customized.rpc.test.config;

import com.customized.rpc.client.RpcClient;
import com.customized.rpc.client.bio.BioRpcClient;
import com.customized.rpc.core.bean.processor.RpcProxyBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RpcClientTestConfig {

    @Bean
    public RpcProxyBeanPostProcessor serviceReferenceHandler() {
        RpcClient client = new BioRpcClient("127.0.0.1", 8002);
        return new RpcProxyBeanPostProcessor(client);
    }

}
