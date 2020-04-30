package com.customized.libs.core.libs.dubbo.adaptive.supports;

import com.alibaba.dubbo.common.URL;
import com.customized.libs.core.libs.dubbo.adaptive.FruitGranter;
import com.customized.libs.core.libs.dubbo.adaptive.beans.Apple;
import com.customized.libs.core.libs.dubbo.adaptive.beans.Fruit;
import com.customized.libs.core.libs.dubbo.adaptive.beans.FruitWrapper;

public class AppleFruitGranter implements FruitGranter {

    @Override
    public Fruit grant(FruitWrapper wrapper) {
        return new Apple("苹果");
    }

    @Override
    public String watering(URL url) {
        System.out.println("watering apple");
        return "watering finished";
    }
}
