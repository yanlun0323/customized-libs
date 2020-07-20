package com.customized.libs.customer.zookeeper.subscribe;

import com.alibaba.dubbo.common.utils.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

@Component
@SuppressWarnings("all")
public class ZookeeperRegistrySubscribe {

    private static Logger logger = LoggerFactory.getLogger(ZookeeperRegistrySubscribe.class);

    private static CountDownLatch watch = new CountDownLatch(1);

    private Map<String, Set<String>> NAMESPACE_CACHE = new ConcurrentHashMap<>(128);

    @Value("${_zookeeper.address}")
    private String zookeeperAddress;

    private CuratorFramework client;

    @PostConstruct
    public void setup() throws Exception {
        this.buildClient();

        this.zkWatch("/server");
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


    /**
     * 注册监听
     * TreeCache: 可以将指定的路径节点作为根节点（祖先节点），对其所有的子节点操作进行监听，
     * 呈现树形目录的监听，可以设置监听深度，最大监听深度为 int 类型的最大值。
     */
    private void zkWatch(String path) throws Exception {
        TreeCache treeCache = new TreeCache(client, path);

        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                ChildData eventData = event.getData();
                switch (event.getType()) {
                    case NODE_ADDED:
                        logger.warn(path + "节点添加" + eventData.getPath() + "\t添加数据为：" + new String(eventData.getData()));
                        refreshNamespace(eventData.getPath(), new String(eventData.getData()));
                        break;
                    case NODE_UPDATED:
                        logger.warn(eventData.getPath() + "节点数据更新\t更新数据为："
                                + new String(eventData.getData()) + "\t版本为：" + eventData.getStat().getVersion());
                        refreshNamespace(eventData.getPath(), new String(eventData.getData()));
                        break;
                    case NODE_REMOVED:
                        logger.warn(eventData.getPath() + "节点被删除");
                        refreshNamespace(eventData.getPath(), null);
                        break;
                    default:
                        break;
                }
            }
        });

        treeCache.start();
        watch.await();  //如果不执行 watch.countDown()，进程会一致阻塞在 watch.await()
    }

    private void refreshNamespace(String key, String data) {
        logger.warn("==> Dubbo Server Namesapce Register..");

        Set<String> childs = NAMESPACE_CACHE.getOrDefault(key, new LinkedHashSet<>());

        if (StringUtils.isEmpty(data)) {
            NAMESPACE_CACHE.remove(key);
        } else {
            childs.add(data);
            NAMESPACE_CACHE.putIfAbsent(key, childs);
        }

        logger.warn("==> Current Namesapce: {}", NAMESPACE_CACHE);
    }
}
