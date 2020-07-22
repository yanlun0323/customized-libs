package com.customized.libs.core.libs.rpc.netty.base.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

import java.nio.charset.Charset;

/**
 * 处理服务端返回的数据
 *
 * @author Administrator
 */
public class RpcClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof ByteBuf) {
            String value = ((ByteBuf) msg).toString(Charset.defaultCharset());
            System.out.println("The Sever Response Data ==> " + value + "\r\n");

            AttributeKey<String> key = AttributeKey.valueOf("ServerData");
            ctx.channel().attr(key).set(value);

            //把客户端的通道关闭
            ctx.channel().close();
        } else {
            AttributeKey<String> key = AttributeKey.valueOf("ServerData");
            ctx.channel().attr(key).set("Unknown Error");

            //把客户端的通道关闭
            ctx.channel().close();
        }
    }

}