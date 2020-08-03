package com.customized.rpc.core.configuration;

import com.customized.rpc.server.RpcServer;
import com.customized.rpc.server.ServiceRegistry;
import com.customized.rpc.server.bio.BioRpcServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yan
 */
@Configuration
public class RpcServerConfig {

    @Bean
    public RpcServer createServer() {
        return new BioRpcServer(8002);
    }

    /**
     * 服务自动注册
     *
     * @return
     */
    @Bean
    public ServiceRegistry serviceRegistry() {
        return new ServiceRegistry();
    }
}
