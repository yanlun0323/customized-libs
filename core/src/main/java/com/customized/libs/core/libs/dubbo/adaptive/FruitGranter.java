package com.customized.libs.core.libs.dubbo.adaptive;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;
import com.customized.libs.core.libs.dubbo.adaptive.beans.Fruit;
import com.customized.libs.core.libs.dubbo.adaptive.beans.FruitWrapper;

/**
 * @author yan
 */
@SPI("apple")
public interface FruitGranter {

    @Adaptive("apple")
    Fruit grant(FruitWrapper wrapper);

    /**
     * 自动适配
     *
     * @param url
     * @return
     */
    @Adaptive
    String watering(URL url);
}