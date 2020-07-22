package com.customized.libs.core.libs.rpc.netty.base.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.customized.libs.core.libs.netty.message.NettyMessageWrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

import static com.customized.libs.core.libs.netty.cfg.NettyConfig.JOIN_DELIMITER_PATTERN;

/**
 * @author yan
 */
public class RpcServerHandler extends ChannelInboundHandlerAdapter {

    private Map<String, Class> serviceMapper;
    private Map<String, Method> methodMapper;

    private int counter;

    public RpcServerHandler(Map<String, Class> serviceMapper, Map<String, Method> methodMapper) {
        this.serviceMapper = serviceMapper;
        this.methodMapper = methodMapper;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        try {
            String data = (String) msg;
            System.out.println("Rpc Server Handler " + (++counter) + " ==> " + data);

            // 调用对应的方法
            String[] components = data.split(JOIN_DELIMITER_PATTERN);

            Class target = serviceMapper.get(components[0]);
            if (Objects.isNull(target)) {
                throw new ClassNotFoundException("Class " + components[0] + " Not Found!");
            }

            Method method = methodMapper.get(components[2]);

            // 参数组装
            Class<?>[] parameterTypes = method.getParameterTypes();
            Object[] args = new Object[parameterTypes.length];
            JSONArray argsObject = JSON.parseArray(components[3]);
            for (int i = 0; i < parameterTypes.length; i++) {
                // JSON格式数据
                if (argsObject.getString(i).contains("{")) {
                    args[i] = JSON.parseObject(argsObject.getString(i), parameterTypes[i]);
                } else {
                    args[i] = argsObject.get(i);
                }
            }

            Object result = method.invoke(target.newInstance(), args);

            byte[] theRespData = NettyMessageWrapper.build2Bytes(JSON.toJSONString(result));
            ByteBuf resp = Unpooled.copiedBuffer(theRespData);
            ctx.write(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println(cause.toString());
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client " + ctx.channel().remoteAddress() + " Connection Opened!! \r\n");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client " + ctx.channel().remoteAddress() + " Connection Closed!! \r\n");
        super.channelInactive(ctx);
    }
}