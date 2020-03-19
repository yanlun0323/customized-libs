package com.customized.libs.customer.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.customized.libs.dubbo.api.CommonDubboProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author yan
 */
@Service
public class CommonDubboInvokerService {

    private static Logger logger = LoggerFactory.getLogger(CommonDubboInvokerService.class);

    @Reference(version = "${common.service.version}")
    private CommonDubboProvider commonDubboProvider;

    @SuppressWarnings("all")
    public void invoke() {
        for (int i = 0; i < 1000; i++) {
            logger.warn("<! The Invoke Result ==> {}",
                    this.commonDubboProvider.helloMsg("小马哥（mercyblitz）")
            );
        }
    }

}
