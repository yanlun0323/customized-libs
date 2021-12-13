/**
 * Project: dubbo.governance-2.2.0-SNAPSHOT
 * <p>
 * File Created at Mar 31, 2012
 * $Id: SyncUtils.java 184666 2012-07-05 11:13:17Z tony.chenl $
 * <p>
 * Copyright 1999-2100 Alibaba.com Corporation Limited.
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Alibaba Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Alibaba.com.
 */
package com.customized.libs.customer.dubboclub.dk.admin.sync.util;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.cluster.Configurator;
import com.alibaba.dubbo.rpc.cluster.ConfiguratorFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ding.lid
 */
public class SyncUtils {

    public static final String SERVICE_FILTER_KEY = ".service";

    public static final String ADDRESS_FILTER_KEY = ".address";

    public static final String ID_FILTER_KEY = ".id";


    private static final ConfiguratorFactory CONFIGURATOR_FACTORY = ExtensionLoader.getExtensionLoader(ConfiguratorFactory.class).getAdaptiveExtension();

    public static String generateServiceKey(URL url) {
        String inf = url.getServiceInterface();
        if (inf == null) return null;
        StringBuilder buf = new StringBuilder();
        String group = url.getParameter(Constants.GROUP_KEY);
        if (group != null && group.length() > 0) {
            buf.append(group).append("/");
        }
        buf.append(inf);
        String version = url.getParameter(Constants.VERSION_KEY);
        if (version != null && version.length() > 0) {
            buf.append(":").append(version);
        }
        return buf.toString();
    }

    public static Configurator toConfigurators(URL url) {
        if (Constants.EMPTY_PROTOCOL.equals(url.getProtocol())) {
            return null;
        }
        Map<String, String> override = new HashMap<String, String>(url.getParameters());
        //override 上的anyhost可能是自动添加的，不能影响改变url判断
        override.remove(Constants.ANYHOST_KEY);
        if (override.size() == 0) {
            return null;
        }
        return CONFIGURATOR_FACTORY.getConfigurator(url);

    }
}