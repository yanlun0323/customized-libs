package com.customized.libs.provider.config;

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

import java.util.List;
import java.util.Properties;

/**
 * @author yan
 */
public class SentinelRuleNacosConfig implements InitFunc {

    private static final String REMOTE_ADDRESS = "172.19.80.15";
    private static final String GROUP_ID = "DUBBO_PROVIDER_SENTINEL_GROUP";
    private static final String NACOS_NAMESPACE = "Dubbo-Provider-Sentinel-Rules";

    private static final String CLIENT_FLOW_RULES_DATA_ID = "cluster-flow-rules";
    private static final String CLIENT_CONFIG_DATA_ID = "cluster-client-config";

    /**
     * 引入Namespace后，在初始化NacosDataSource时需要指定Namespace(PropertyKeyConst.NAMESPACE)
     */
    @Override
    public void init() {
        Properties defaultProperties = buildProperties(REMOTE_ADDRESS, NACOS_NAMESPACE);

        // step:0 remoteAddress 代表 Nacos 服务端的地址，groupId 和 dataId 对应 Nacos 中相应配置
        ReadableDataSource<String, List<FlowRule>> ruleDs = new NacosDataSource<>(
                defaultProperties, GROUP_ID, CLIENT_FLOW_RULES_DATA_ID,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                })
        );
        FlowRuleManager.register2Property(ruleDs.getProperty());


        // 这个状态配置很重要！！顺序也重要！！（需要在step1之前ApplyState）

        /*
          1、ClusterStatePropertyListener

          2、ClusterStateManager.setToClient()

          3、DefaultClusterTokenClient()

          4、初始化 SERVER_CHANGE_OBSERVERS

          5、完成监听器注册，可监听集群模式下的数据源变化
         */
        ClusterStateManager.applyState(ClusterStateManager.CLUSTER_CLIENT);

        // step:1 初始化一个配置ClusterClientConfig的 Nacos 数据源
        ReadableDataSource<String, ClusterClientConfig> clientConfigDs = new NacosDataSource<>(
                defaultProperties, GROUP_ID, CLIENT_CONFIG_DATA_ID,
                source -> JSON.parseObject(source, new TypeReference<ClusterClientConfig>() {
                })
        );
        ClusterClientConfigManager.registerClientConfigProperty(clientConfigDs.getProperty());

        // ClusterClientAssignConfig
        ReadableDataSource<String, ClusterClientAssignConfig> clientAssignDs = new NacosDataSource<>(
                defaultProperties, GROUP_ID, CLIENT_CONFIG_DATA_ID,
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
