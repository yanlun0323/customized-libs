package com.customized.libs.core.libs.rpc.netty.base;

import com.alibaba.fastjson.JSON;
import com.customized.libs.core.libs.netty.cfg.NettyConfig;
import com.customized.libs.core.libs.rpc.RpcClient;
import com.customized.libs.core.libs.rpc.netty.base.core.RpcClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

import static com.customized.libs.core.libs.netty.cfg.NettyConfig.DEFAULT_DELIMITER_KEY;
import static com.customized.libs.core.libs.netty.cfg.NettyConfig.JOIN_DELIMITER_KEY;

@Slf4j
public class NettyRpcClientImpl<T> implements RpcClient<T> {

    private Bootstrap client = new Bootstrap();

    private InetSocketAddress socketAddress;

    @Override
    public void connect(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;

        EventLoopGroup group = new NioEventLoopGroup();
        client.group(group);

        client.channel(NioSocketChannel.class);

        client.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) {
                // 当定义了delimiter后，则客户端/服务端对应的通讯都需要通过这个结尾，否则会出现通讯异常（不抛出任何异常，很诧异）
                // 字符串编码，一定要加在SimpleClientHandler前面
                ch.pipeline().addLast(new StringEncoder());
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, NettyConfig.DEFAULT_DELIMITER));
                ch.pipeline().addLast(new RpcClientHandler());
            }
        });
    }

    @Override
    @SuppressWarnings("all")
    public <T> T getInvoker(Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                try {
                    // service + method + parameterTypes + args
                    ChannelFuture future;
                    try {
                        future = client.connect(socketAddress.getHostName(), socketAddress.getPort()).sync();

                        future.channel().write(String.format("%s%s%s", service.getSimpleName(), JOIN_DELIMITER_KEY, method.getName()));
                        future.channel().write(String.format("%s%s", JOIN_DELIMITER_KEY, method.toGenericString()));
                        future.channel().write(String.format("%s%s", JOIN_DELIMITER_KEY, JSON.toJSONString(args)));
                        future.channel().writeAndFlush(DEFAULT_DELIMITER_KEY);
                        future.channel().closeFuture().sync();

                        //接收服务端返回的数据
                        AttributeKey<String> key = AttributeKey.valueOf("ServerData");
                        return future.channel().attr(key).get();
                    } catch (InterruptedException e) {
                        log.error("==> Handle Failed!!", e);
                    }
                } catch (Exception e) {
                    log.error("==> getInvoker Failed!", e);
                }
                return null;
            }
        });

    }
}
