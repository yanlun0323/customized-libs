package com.customized.libs.core.libs.netty.message;

import com.customized.libs.core.libs.netty.cfg.NettyConfig;

/**
 * @author yan
 */
public class NettyMessageWrapper {

    private String data;

    public static String build(Object data) {
        return String.format("%s%s", data, NettyConfig.DEFAULT_DELIMITER_KEY);
    }

    public static byte[] build2Bytes(Object data) {
        return String.format("%s%s", data, NettyConfig.DEFAULT_DELIMITER_KEY).getBytes();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
