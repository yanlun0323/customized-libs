package com.customized.libs.shardingsphere.entity.common;

/**
 * @author yan
 */
public class CommResp {

    private String key;

    private String msg;

    private Object data;

    public CommResp() {

    }

    public CommResp(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
