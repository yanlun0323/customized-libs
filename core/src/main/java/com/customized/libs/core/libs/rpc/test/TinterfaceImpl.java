package com.customized.libs.core.libs.rpc.test;

public class TinterfaceImpl implements Tinterface {

    @Override
    public String send(String msg) {
        return "Send Message ==>  " + msg;
    }
}