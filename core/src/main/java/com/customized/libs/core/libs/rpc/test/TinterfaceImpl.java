package com.customized.libs.core.libs.rpc.test;

import com.alibaba.fastjson.JSON;

public class TinterfaceImpl implements Tinterface {

    @Override
    public String send(String msg) {
        return "Send String ==>  " + msg;
    }

    @Override
    public String send(User data, Integer index) {
        return "Send Index " + index + " User ==>" + JSON.toJSONString(data);
    }
}