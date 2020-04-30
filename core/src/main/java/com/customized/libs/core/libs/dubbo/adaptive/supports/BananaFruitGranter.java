package com.customized.libs.core.libs.dubbo.adaptive.supports;

import com.alibaba.dubbo.common.URL;
import com.customized.libs.core.libs.dubbo.adaptive.FruitGranter;
import com.customized.libs.core.libs.dubbo.adaptive.beans.Banana;
import com.customized.libs.core.libs.dubbo.adaptive.beans.Fruit;
import com.customized.libs.core.libs.dubbo.adaptive.beans.FruitWrapper;

public class BananaFruitGranter implements FruitGranter {

    @Override
    public Fruit grant(FruitWrapper wrapper) {
        return new Banana("香蕉");
    }

    @Override
    public String watering(URL url) {
        System.out.println("watering banana");
        return "watering finished";
    }
}
