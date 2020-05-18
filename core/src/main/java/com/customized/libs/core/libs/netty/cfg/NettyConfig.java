package com.customized.libs.core.libs.netty.cfg;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author yan
 */
public class NettyConfig {

    public static final String DEFAULT_DELIMITER_KEY = "$END$";

    public static final ByteBuf DEFAULT_DELIMITER = Unpooled.copiedBuffer(NettyConfig.DEFAULT_DELIMITER_KEY.getBytes());
}
