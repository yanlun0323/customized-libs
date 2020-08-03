package com.customized.rpc.core.protocol;

import java.io.Serializable;

public class RpcResponse implements Serializable {

    private static final long serialVersionUID = 2478818813087860203L;

    /**
     * 响应状态 0-失败，1-成功
     */
    private int status;

    /**
     * 错误信息
     */
    private String error;

    /**
     * 响应数据
     */
    private Object data;

    public static RpcResponse ok(Object data) {
        return build(1, null, data);
    }

    public static RpcResponse fail(String error) {
        return build(0, error, null);
    }

    public static RpcResponse build(int status, String error, Object data) {
        RpcResponse theRsp = new RpcResponse();
        theRsp.setStatus(status);
        theRsp.setError(error);
        theRsp.setData(data);
        return theRsp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
