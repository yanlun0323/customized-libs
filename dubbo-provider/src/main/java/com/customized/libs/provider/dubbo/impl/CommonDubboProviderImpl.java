package com.customized.libs.provider.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.customized.libs.dubbo.api.CommonDubboProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yan
 */
@Service(interfaceClass = CommonDubboProvider.class, version = "1.0")
public class CommonDubboProviderImpl implements CommonDubboProvider {

    private static Logger logger = LoggerFactory.getLogger(CommonDubboProviderImpl.class);

    /**
     * 利用@NacosValue注解可配置动态刷新，@Value则无法完成此功能
     */
    @NacosValue(value = "${default.keywords}", autoRefreshed = true)
    private String defaultKeywords;

    @Override
    public String helloMsg(String userName) {
        userName = StringUtils.defaultString(userName, "N/A");

        RpcContext rpcContext = RpcContext.getContext();
        String tag = String.format("Service [name :%s , port : %d] %s(\"%s\") : Hello,%s",
                defaultKeywords,
                rpcContext.getLocalPort(),
                rpcContext.getMethodName(),
                userName,
                userName
        );
        logger.warn("<<< {}", tag);
        return String.format("%s %s!", defaultKeywords, userName);
    }
}
