package com.customized.libs.libs.dispatcher;

import com.alibaba.dubbo.common.extension.SPI;

/**
 * @author yan
 */
@SPI
public interface Register {


    void doRegister();
}
