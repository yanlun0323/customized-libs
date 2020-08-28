package com.customized.libs.provider.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.customized.libs.dubbo.api.UserDubboProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yan
 */
@Service(interfaceClass = UserDubboProvider.class, version = "${common.service.version}")
public class UserDubboProviderImpl implements UserDubboProvider {

    private static Logger logger = LoggerFactory.getLogger(UserDubboProviderImpl.class);

    /**
     * 利用@NacosValue注解可配置动态刷新，@Value则无法完成此功能
     */
    @NacosValue(value = "${default.keywords}", autoRefreshed = true)
    private String defaultKeywords;

    @Override
    public String getUser(String userName) {
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

    @Override
    public String getUser() {
        return String.format("%s %s!", defaultKeywords, "empty");
    }
}
