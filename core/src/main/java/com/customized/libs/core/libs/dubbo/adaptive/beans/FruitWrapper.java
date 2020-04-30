package com.customized.libs.core.libs.dubbo.adaptive.beans;

import com.alibaba.dubbo.common.URL;

/**
 * @author yan
 */
public class FruitWrapper {

    public FruitWrapper(URL url) {
        this.url = url;
    }

    private URL url;

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
