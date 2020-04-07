package com.customized.libs.core.config;

import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.customized.libs.core.utils.SpringContextLoader;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Properties;

/**
 * @author yan
 */
public class SentinelRuleNacosInitFunc implements InitFunc {

    /**
     * InitFunc 是在首次调用 SphU.entry(KEY) 方法时触发的，注册的初始化函数会依次执行。
     * <p>
     * 如果你不想把初始化的工作延后到第一次调用时触发，可以手动调用 InitExecutor.doInit() 函数，重复调用只会执行一次。
     * <p>
     * 引入Namespace后，在初始化NacosDataSource时需要指定Namespace(PropertyKeyConst.NAMESPACE)
     */
    @Override
    public void init() {
        Environment environment = SpringContextLoader.getEnvironment();

        String nacosRemoteAddress = environment.getProperty("core.nacos.remote.addr");
        String nacosGroupId = environment.getProperty("core.nacos.group.id");
        String nacosNamespace = environment.getProperty("core.nacos.namespace");
        String nacosFlowRulesDataId = environment.getProperty("core.nacos.flow.rules.dataId");
        String nacosClientConfigDataId = environment.getProperty("core.nacos.client.config.dataId");

        Properties defaultProperties = buildProperties(nacosRemoteAddress, nacosNamespace);

        // step:0 remoteAddress 代表 Nacos 服务端的地址，groupId 和 dataId 对应 Nacos 中相应配置
        ReadableDataSource<String, List<FlowRule>> ruleDs = new NacosDataSource<>(
                defaultProperties, nacosGroupId, nacosFlowRulesDataId,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                })
        );
        FlowRuleManager.register2Property(ruleDs.getProperty());


        // 这个状态配置很重要！！顺序也重要！！（需要在step1之前ApplyState）
        ClusterStateManager.applyState(ClusterStateManager.CLUSTER_CLIENT);

        // step:1 初始化一个配置ClusterClientConfig的 Nacos 数据源
        ReadableDataSource<String, ClusterClientConfig> clientConfigDs = new NacosDataSource<>(
                defaultProperties, nacosGroupId, nacosClientConfigDataId,
                source -> JSON.parseObject(source, new TypeReference<ClusterClientConfig>() {
                })
        );
        ClusterClientConfigManager.registerClientConfigProperty(clientConfigDs.getProperty());

        // ClusterClientAssignConfig
        ReadableDataSource<String, ClusterClientAssignConfig> clientAssignDs = new NacosDataSource<>(
                defaultProperties, nacosGroupId, nacosClientConfigDataId,
                source -> JSON.parseObject(source, new TypeReference<ClusterClientAssignConfig>() {
                })
        );
        ClusterClientConfigManager.registerServerAssignProperty(clientAssignDs.getProperty());
    }

    private static Properties buildProperties(String serverAddr, String namespace) {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, serverAddr);
        properties.setProperty(PropertyKeyConst.NAMESPACE, namespace);
        return properties;
    }
}
