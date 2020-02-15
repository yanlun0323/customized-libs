package com.customized.libs.libs.xmlparser.entity;


import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Body {

    @XStreamAlias("Base")
    private BodyBase base;

    public BodyBase getBase() {
        return base;
    }

    public void setBase(BodyBase base) {
        this.base = base;
    }
}
