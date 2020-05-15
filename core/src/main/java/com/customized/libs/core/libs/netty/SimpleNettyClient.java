package com.customized.libs.core.libs.netty;


import com.customized.libs.core.libs.netty.cfg.NettyConfig;
import com.customized.libs.core.libs.netty.handler.SimpleClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;

import java.util.Scanner;

/**
 * @author yan
 */
public class SimpleNettyClient {

    public static void main(String[] args) throws InterruptedException {
        // 首先，Netty通过ServerBootstrap启动服务端
        Bootstrap client = new Bootstrap();

        // 第一步 定义线程组，处理读写和链接事件
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        client.group(eventLoopGroup);

        // 第二步 绑定客户端通道
        client.channel(NioSocketChannel.class);

        // 第三步 给NioSocketChannel初始化handler，处理读写事件
        client.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) {
                // 当定义了delimiter后，则客户端/服务端对应的通讯都需要通过这个结尾，否则会出现通讯异常（不抛出任何异常，很诧异）
                ByteBuf delimiter = Unpooled.copiedBuffer(NettyConfig.DEFAULT_DELIMITER.getBytes());
                // 字符串编码，一定要加在SimpleClientHandler前面
                ch.pipeline().addLast(new StringEncoder());
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, delimiter));
                ch.pipeline().addLast(new SimpleClientHandler());
            }
        });

        Scanner scanner = new Scanner(System.in);
        while (Boolean.TRUE) {
            String cmd = scanner.next();
            String data = null;

            if ("SEND".equals(cmd)) {
                data = scanner.next();
            }

            // 第四步 连接服务器
            ChannelFuture future = client.connect("localhost", 9888).sync();
            future.channel().writeAndFlush(data);
            future.channel().writeAndFlush(NettyConfig.DEFAULT_DELIMITER);
            future.channel().closeFuture().sync();

            //接收服务端返回的数据
            AttributeKey<String> key = AttributeKey.valueOf("ServerData");
            Object result = future.channel().attr(key).get();
            System.out.println("Server Response Data: " + result.toString());
        }
    }
}