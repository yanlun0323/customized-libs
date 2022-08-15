package com.customized.libs.core.framework.rpc.remoting.exchange;

import com.alibaba.dubbo.common.utils.StringUtils;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/6/15 13:46
 */
public class Request implements Serializable {

    private static final AtomicLong INVOKE_ID = new AtomicLong(0);

    private static final long serialVersionUID = 1948272570461948536L;

    private final long mId;

    private Object mData;

    private String key;

    public Request() {
        mId = newId();
    }

    public Request(long id) {
        mId = id;
    }

    private static long newId() {
        // getAndIncrement() When it grows to MAX_VALUE, it will grow to MIN_VALUE, and the negative can be used as ID
        return INVOKE_ID.getAndIncrement();
    }

    private static String safeToString(Object data) {
        if (data == null) return null;
        String dataStr;
        try {
            dataStr = data.toString();
        } catch (Throwable e) {
            dataStr = "<Fail toString of " + data.getClass() + ", cause: " +
                    StringUtils.toString(e) + ">";
        }
        return dataStr;
    }

    public long getId() {
        return mId;
    }

    public Object getData() {
        return mData;
    }

    public void setData(Object mData) {
        this.mData = mData;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Request [id=" + mId + ", data=" + (mData == this ? "this" : safeToString(mData)) + "]";
    }
}
