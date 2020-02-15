package com.customized.libs.libs.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author yan
 */
public class TimeServer {

    public void bind(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            // 最大连接数量设定 BACKLOG用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，
            // 用于临时存放已完成三次握手的请求的队列的最大长度。如果未设置或所设置的值小于1，Java将使用默认值50。
            b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).
                    option(ChannelOption.SO_BACKLOG, 1024).childHandler(new ChildChannelHandler());
            // 绑定端口，等待同步成功
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        /**
         * 执行顺序 DelimiterBasedFrameDecoder --> StringDecoder --> TimeServerHandler
         * 1. 对于channelInboundHandler,总是会从传递事件的开始，向链表末尾方向遍历执行可用的inboundHandler。
         * <p>
         * 2. 对于channelOutboundHandler，总是会从write事件执行的开始，向链表头部方向遍历执行可用的outboundHandler。
         * <br/>
         * <br/>
         * 版权声明：本文为CSDN博主「intotw」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
         * 原文链接：https://blog.csdn.net/chin_sung/article/details/79612708
         *
         * @param socketChannel
         */
        @Override
        protected void initChannel(SocketChannel socketChannel) {
            ByteBuf delimiter = Unpooled.copiedBuffer("$_$".getBytes());
            socketChannel.pipeline().addFirst(new TimeServerOutHandler());

            // 如果TimeServerOutHandler添加在了InHandler后面，则Read事件执行完后，像链表头方向遍历，则找不到该Handler
            // 为什么要按照方向区分Read/Write事件？此处完全可以Read事件遍历方向为表头->表尾；Write事件遍历方向表尾->表头
            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, delimiter));
            socketChannel.pipeline().addLast(new StringDecoder());
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args) {
        int port = 9000;
        try {
            new TimeServer().bind(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}