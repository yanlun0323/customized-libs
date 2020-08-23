package com.customized.lib.web.service;

import com.customized.lib.web.service.ip.region.IPHelper;
import org.junit.Test;

public class IPHelperTest {

    @Test
    public void Ip2region() {
        System.out.println(IPHelper.getCityInfo("182.50.124.211"));
        System.out.println(IPHelper.getCityInfo("222.247.64.76"));
        System.out.println(IPHelper.getCityInfo("14.215.177.38"));
        System.out.println(IPHelper.getCityInfo("114.80.221.198"));
    }
}
