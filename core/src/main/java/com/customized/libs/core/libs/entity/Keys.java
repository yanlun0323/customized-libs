package com.customized.libs.core.libs.entity;

import lombok.Builder;

@Builder
public class Keys {

    private String k;

    private String v;

    public Keys(String k, String v) {
        this.k = k;
        this.v = v;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }
}
