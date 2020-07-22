package com.customized.libs.core.libs.rpc.netty.base;

import com.customized.libs.core.libs.netty.cfg.NettyConfig;
import com.customized.libs.core.libs.rpc.RpcServer;
import com.customized.libs.core.libs.rpc.netty.base.core.RpcServerHandler;
import com.customized.libs.core.libs.rpc.netty.base.core.RpcServerOutHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yan
 */
@Slf4j
public class NettyRpcServerImpl implements RpcServer {

    private static final Map<String, Class> SERVICE_REGISTER = new ConcurrentHashMap<>(128);

    private static final Map<String, Method> METHOD_REGISTER = new ConcurrentHashMap<>(512);

    @Override
    public void export(Class service, Class impl) {
        SERVICE_REGISTER.put(service.getSimpleName(), impl);
        // Method Register
        Method[] methods = service.getDeclaredMethods();
        for (Method method : methods) {
            METHOD_REGISTER.put(method.toGenericString(), method);
        }
    }

    @Override
    public void open(int port) {
        EventLoopGroup boos = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();

        try {

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boos, work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new ChildChannelHandler());

            // 绑定端口
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            log.error("==> Server Open Failed!", e);
        } finally {
            boos.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        /**
         * 执行顺序 DelimiterBasedFrameDecoder --> StringDecoder --> SimpleServerHandler
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
            socketChannel.pipeline().addFirst(new RpcServerOutHandler());

            // 如果TimeServerOutHandler添加在了InHandler后面，则Read事件执行完后，向链表头方向遍历，则找不到该Handler
            // 为什么要按照方向区分Read/Write事件？此处完全可以Read事件遍历方向为表头->表尾；Write事件遍历方向表尾->表头
            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, NettyConfig.DEFAULT_DELIMITER));
            socketChannel.pipeline().addLast(new StringDecoder());

            socketChannel.pipeline().addLast(new RpcServerHandler(SERVICE_REGISTER, METHOD_REGISTER));
        }
    }
}
