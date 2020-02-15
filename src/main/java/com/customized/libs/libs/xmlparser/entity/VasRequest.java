package com.customized.libs.libs.xmlparser.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias(value = "Packet")
public class VasRequest {

    @XStreamAsAttribute
    private String type = "REQUEST";
    @XStreamAsAttribute
    private String version = "1.0";

    @XStreamAlias("Head")
    private Head head;

    @XStreamAlias("Body")
    private Body body;

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
