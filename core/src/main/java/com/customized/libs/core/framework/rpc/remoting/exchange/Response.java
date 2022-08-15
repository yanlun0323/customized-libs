package com.customized.libs.core.framework.rpc.remoting.exchange;

import java.io.Serializable;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/6/15 13:46
 */
public class Response implements Serializable {

    private static final long serialVersionUID = 1381432054206131287L;
    private long mId = 0;

    private Object mResult;

    public Response() {
    }

    public Response(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public Object getResult() {
        return mResult;
    }

    public void setResult(Object mResult) {
        this.mResult = mResult;
    }

    @Override
    public String toString() {
        return "Response [id=" + mId + ", result=" + (mResult == this ? "this" : mResult) + "]";
    }
}
