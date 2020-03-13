package com.customized.libs.customer.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.customized.libs.dubbo.api.CommonDubboProvider;
import org.springframework.stereotype.Service;

/**
 * @author yan
 */
@Service
public class CommonDubboInvokerService {

    @Reference(version = "${common.service.version}")
    private CommonDubboProvider commonDubboProvider;

    public void invoke() {
        for (int i = 0; i < 10; i++) {
            System.out.println(commonDubboProvider.helloMsg("小马哥（mercyblitz）"));
        }
    }

}
