package com.customized.libs.provider.registry;

import com.customized.libs.dubbo.api.utils.MachineUtil;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author yan
 */
@Component
@SuppressWarnings("all")
public class ZookeeperRegistry {

    private static Logger logger = LoggerFactory.getLogger(ZookeeperRegistry.class);

    @Value("${_zookeeper.address}")
    private String zookeeperAddress;

    private CuratorFramework client;

    @PostConstruct
    public void setup() throws Exception {
        this.buildClient();
        this.createChildPath();
    }

    private void buildClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.builder()
                .connectString(zookeeperAddress)
                .sessionTimeoutMs(5000)  // 会话超时时间
                .connectionTimeoutMs(5000) // 连接超时时间
                .retryPolicy(retryPolicy)
                .namespace("dubbo-provider") // 包含隔离名称
                .build();
        client.start();
    }

    public void createChildPath() throws Exception {
        String data = String.format("ip:%s", MachineUtil.getFristIPv4());
        client.create().creatingParentContainersIfNeeded() // 递归创建所需父节点
                .withMode(CreateMode.EPHEMERAL)
                .forPath("/setup", data.getBytes()); // 目录及内容
        logger.warn("==> Dubbo Provider Zookeeper Monitor Starting...");
    }
}
