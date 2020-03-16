package com.customized.libs.extension.starter;

import com.alibaba.csp.sentinel.cluster.server.ClusterTokenServer;
import com.alibaba.csp.sentinel.cluster.server.SentinelDefaultTokenServer;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;
import com.customized.libs.extension.ClusterConstants;
import com.customized.libs.extension.init.ClusterServerInitFunc;

import java.util.Collections;

/**
 * <p>Cluster server demo (alone mode).</p>
 * <p>Here we init the cluster server dynamic data sources in
 * {@link com.customized.libs.extension.init.ClusterServerInitFunc}.</p>
 *
 * @author Eric Zhao
 * @since 1.4.0
 */
public class ClusterServerStarter {

    public static void main(String[] args) throws Exception {
        // Not embedded mode by default (alone mode).
        ClusterTokenServer tokenServer = new SentinelDefaultTokenServer();

        // A sample for manually load config for cluster server.
        // It's recommended to use dynamic data source to cluster manage config and rules.
        // See the sample in ClusterServerInitFunc for detail.
        ClusterServerConfigManager.loadGlobalTransportConfig(new ServerTransportConfig()
                .setIdleSeconds(600)
                .setPort(8718));
        ClusterServerConfigManager.loadServerNamespaceSet(Collections.singleton(ClusterConstants.APP_NAME));

        // Start the server.
        tokenServer.start();
    }
}