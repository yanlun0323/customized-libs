package com.customized.libs.core.libs.netty;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;

import java.util.Scanner;

/**
 * @author yan
 */
public class NettyClient {

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
                // 字符串编码，一定要加在SimpleClientHandler前面
                ch.pipeline().addLast(new StringEncoder());
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE,
                        Delimiters.lineDelimiter()[0]));
                ch.pipeline().addLast(new SimpleClientHandler());
            }
        });

        String defaultMsg = "don't know what I do now is right, those are wrong, and when I finally Laosi when I know these. So I can do now is to try to do well in everything, and then wait to die a natural death.Sometimes I can be very happy to talk to everyone, can be very presumptuous, but no one knows, it is but very deliberatelycamouflage, camouflage; I can make him very happy very happy, but couldn't find the source of happiness, just giggle\n" +
                "If not to the sun for smiling, warm is still in the sun there, but wewill laugh more confident calm; if turned to found his own shadow, appropriate escape, the sun will be through the heart,warm each place behind the corner; if an outstretched palm cannot fall butterfly, then clenched waving arms, given power; if I can't have bright smile, it will face to the sunshine, and sunshine smile together, in full bloom.\n" +
                "Time is like a river, the left bank is unable to forget the memories, right is worth grasp the youth, the middle of the fast flowing, is the sad young faint. There are many good things, buttruly belong to own but not much. See the courthouse blossom,honor or disgrace not Jing, hope heaven Yunjuanyunshu, has no intention to stay. LockInterruptibly this round the world, all can learn to use a normal heart to treat all around, is also a kind of realm!\n";

        Scanner scanner = new Scanner(System.in);
        while (Boolean.TRUE) {
            String data = scanner.next();
            if ("M1".equals(data)) {
                data = defaultMsg;
            }
            // 第四步 连接服务器
            ChannelFuture future = client.connect("localhost", 9000).sync();
            future.channel().writeAndFlush(data);

            future.channel().writeAndFlush("$_$");
            future.channel().closeFuture().sync();


            //接收服务端返回的数据
            AttributeKey<String> key = AttributeKey.valueOf("ServerData");
            Object result = future.channel().attr(key).get();
            System.out.println(result.toString());
        }
    }
}