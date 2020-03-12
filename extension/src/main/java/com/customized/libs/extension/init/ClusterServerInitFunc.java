package com.customized.libs.extension.init;

import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterParamFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.customized.libs.extension.ClusterConstants;

import java.util.List;
import java.util.Set;

/**
 * @author Eric Zhao
 */
public class ClusterServerInitFunc implements InitFunc {

    private final String remoteAddress = "localhost";
    private final String groupId = "SENTINEL_GROUP";
    private final String namespaceSetDataId = "cluster-server-namespace-set";
    private final String serverTransportDataId = "cluster-server-transport-config";

    @Override
    public void init() {
        // Register cluster flow rule property supplier which creates data source by namespace.
        ClusterFlowRuleManager.setPropertySupplier(namespace -> {
            ReadableDataSource<String, List<FlowRule>> ds = new NacosDataSource<>(remoteAddress, groupId,
                    namespace + ClusterConstants.FLOW_POSTFIX,
                    source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                    }));
            return ds.getProperty();
        });
        // Register cluster parameter flow rule property supplier.
        ClusterParamFlowRuleManager.setPropertySupplier(namespace -> {
            ReadableDataSource<String, List<ParamFlowRule>> ds = new NacosDataSource<>(remoteAddress, groupId,
                    namespace + ClusterConstants.PARAM_FLOW_POSTFIX,
                    source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {
                    }));
            return ds.getProperty();
        });

        // Server namespace set (scope) data source.
        ReadableDataSource<String, Set<String>> namespaceDs = new NacosDataSource<>(remoteAddress, groupId,
                namespaceSetDataId, source -> JSON.parseObject(source, new TypeReference<Set<String>>() {
        }));
        ClusterServerConfigManager.registerNamespaceSetProperty(namespaceDs.getProperty());
        // Server transport configuration data source.
        ReadableDataSource<String, ServerTransportConfig> transportConfigDs = new NacosDataSource<>(remoteAddress,
                groupId, serverTransportDataId,
                source -> JSON.parseObject(source, new TypeReference<ServerTransportConfig>() {
                }));
        ClusterServerConfigManager.registerServerTransportProperty(transportConfigDs.getProperty());
    }
}