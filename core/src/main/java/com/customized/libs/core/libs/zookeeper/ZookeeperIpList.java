package com.customized.libs.core.libs.zookeeper;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.utils.Assert;
import com.alibaba.dubbo.common.utils.UrlUtils;
import com.google.common.base.Joiner;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ZookeeperIpList {

    private String dataId = "com.cardinfo.mpos.interfaces.dubbo.service.terminal.TerminalActivityRPCService/providers:1.0";

    private URL CONSUMER_URL;

    private static final Joiner JOINER = Joiner.on("|").useForNull("nil");

    public final List<String> getIpList() {
        return ipList;
    }

    private volatile List<String> ipList = new ArrayList<String>();

    /**
     * 对获取的列表内容进行过滤
     * <p>
     * dubbo%3A%2F%2F172.19.80.15%3A13010%2Fcom.cardinfo.mpos.interfaces.dubbo.service.terminal.TerminalActivityRPCService%3Fanyhost%3Dtrue%26application%3Dmposinterfaces%26default.retries%3D0%26default.service.filter%3Ddefault%2Chqfilter%26default.timeout%3D30000%26dispatcher%3Dexecution%26dubbo%3Dmposinterfaces%26interface%3Dcom.cardinfo.mpos.interfaces.dubbo.service.terminal.TerminalActivityRPCService%26methods%3DgetActivitityList%2CgetActivityByTrmSn%26payload%3D163886080%26pid%3D17747%26revision%3Dmposinterfaces%26side%3Dprovider%26threadpool%3Dcached%26timestamp%3D1599038069590%26version%3D1.0
     * <p>
     * dubbo://172.19.80.15:13010/com.cardinfo.mpos.interfaces.dubbo.service.terminal.TerminalActivityRPCService?anyhost=true&application=mposinterfaces&default.retries=0&default.service.filter=default,hqfilter&default.timeout=30000&dispatcher=execution&dubbo=mposinterfaces&interface=com.cardinfo.mpos.interfaces.dubbo.service.terminal.TerminalActivityRPCService&methods=getActivitityList,getActivityByTrmSn&payload=163886080&pid=17747&revision=mposinterfaces&side=provider&threadpool=cached&timestamp=1599038069590&version=1.0
     *
     * @param consumer
     * @param providers
     * @return
     */
    private static List<URL> toUrlsWithoutEmpty(URL consumer, List<String> providers) {
        List<URL> urls = new ArrayList<URL>();
        if (providers != null && providers.size() > 0) {
            urls = providers.stream().map(URL::decode).filter(provider -> provider.contains("://"))
                    .map(URL::valueOf).filter(url -> UrlUtils.isMatch(consumer, url))
                    .collect(Collectors.toList());
        }

        return urls;
    }

    /**
     * 解析服务提供者地址列表为ip:port格式
     *
     * @param ipSet
     */
    private void parseIpList(List<String> ipSet) {

        List<URL> urlList = toUrlsWithoutEmpty(CONSUMER_URL, ipSet);
        this.ipList = urlList.stream().map(URL::getAddress).collect(Collectors.toList());

    }

    public void init(String zkServerAddr, String zkGroup, String dataId, String serviceGroup) {
        // 1.参数校验
        Assert.notNull(zkServerAddr, "zkServerAddr is null.");
        Assert.notNull(dataId, "dataId is null.");
        Assert.notNull(dataId, "zkGroup is null.");
        Assert.notNull(dataId, "serviceGroup is null.");

        // 2.拼接订阅的path
        String[] temp = dataId.split(":");
        if (temp.length != 2) {
            throw new RuntimeException("dataId is illegal");
        }

        this.dataId = "/" + zkGroup + "/" + temp[0] + "/providers";

        String consumeUrl;
        if (StringUtils.isNotBlank(serviceGroup)) {
            consumeUrl = "consumer://172.19.80.15/?group=" + serviceGroup + "&interface=" + temp[0] + "&version="
                    + temp[1];
        } else {
            consumeUrl = "consumer://172.19.80.15/?interface=" + temp[0] + "&version="
                    + temp[1];
        }
        CONSUMER_URL = URL.valueOf(consumeUrl);

        // 3.开启zk，订阅path路径下服务提供者信息，并添加监听器
        System.out.println(JOINER.join("init zk ", zkServerAddr, this.dataId, consumeUrl));
        ZkClient zkClient = new ZkClient(zkServerAddr);
        List<String> list = zkClient.subscribeChildChanges(this.dataId, new IZkChildListener() {

            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                // 3.1解析服务提供者地址列表
                parseIpList(currentChilds);

                try {
                    System.out.println((JOINER.join("ipList changed:", JSON.json(ipList))));
                } catch (IOException e) {
                }
            }
        });

        //4. 解析服务提供者ip列表
        parseIpList(list);

    }

    public static void main(String[] a) throws InterruptedException {
        System.out.println(JOINER.join("ONE", "TWO", "THREE"));
        ZookeeperIpList zk = new ZookeeperIpList();
        zk.init("172.19.80.13:2181", "dubbo",
                "com.cardinfo.mpos.interfaces.dubbo.service.terminal.TerminalActivityRPCService:1.0", null);

        try {
            System.out.println((JOINER.join("parseIpList", JSON.json(zk.getIpList()))));
        } catch (IOException e) {
        }
        Thread.currentThread().join();

    }
}