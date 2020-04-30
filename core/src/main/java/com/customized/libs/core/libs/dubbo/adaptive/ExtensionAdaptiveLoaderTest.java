package com.customized.libs.core.libs.dubbo.adaptive;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.fastjson.JSON;
import com.customized.libs.core.libs.dubbo.adaptive.beans.Fruit;
import com.customized.libs.core.libs.dubbo.adaptive.beans.FruitWrapper;

import java.util.List;

/**
 * @author yan
 */
public class ExtensionAdaptiveLoaderTest {

    private static final String EMPTY = "fruit.granter";

    @SuppressWarnings("all")
    public static void main(String[] args) {
        ExtensionLoader<FruitGranter> extensionLoader = ExtensionLoader.getExtensionLoader(FruitGranter.class);

        URL url = URL.valueOf("dubbo://192.168.0.101:20880?fruit.granter=apple");
        List<FruitGranter> records = extensionLoader.getActivateExtension(url, EMPTY);

        System.out.println(records);

        for (FruitGranter record : records) {
            System.out.println(JSON.toJSONString(record.grant(new FruitWrapper(url))));
        }


        System.out.println();
        System.out.println("Case 2 Show times!!");

        FruitGranter granter = extensionLoader.getAdaptiveExtension();

        // grant方法未标记Adaptive，则调用grant方法会抛出异常
        /*
         * granter.grant();
         * at com.customized.libs.core.libs.dubbo.adaptive.FruitGranter$Adaptive.grant(FruitGranter$Adaptive.java)
         */
        System.out.println(granter.watering(url));

        Fruit target = granter.grant(new FruitWrapper(url));
        System.out.println(JSON.toJSONString(target));
    }
}
