package com.customized.libs.libs.rocketmq;

public class MQEvent {


    private String keySuffix;

    private String tags;

    private Object data;

    public MQEvent() {

    }

    public MQEvent(String keySuffix, Object data) {
        this.keySuffix = keySuffix;
        this.data = data;
    }

    public static MQEvent build(String keySuffix, Object data) {
        return new MQEvent(keySuffix, data);
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getKeySuffix() {
        return keySuffix;
    }

    public void setKeySuffix(String keySuffix) {
        this.keySuffix = keySuffix;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
