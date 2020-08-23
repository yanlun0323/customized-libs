package com.customized.rpc.server;

import com.customized.rpc.core.bean.registry.ServiceRegistry;
import com.customized.rpc.core.protocol.RpcRequest;
import com.customized.rpc.core.protocol.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 处理请求&&获取响应
 */
public class RequestHandler {

    private static Logger log = LoggerFactory.getLogger(RequestHandler.class);

    public static RpcResponse handleRequest(RpcRequest request) {
        // 获取服务
        Object service = ServiceRegistry.getService(request.getClassName());

        if (service == null) {
            log.error("Service(" + request.getClassName() + ") Is Not Exists!!");
            return RpcResponse.fail("Unknown Service");
        } else {
            try {
                Class<?> clazz = service.getClass();
                Method method = clazz.getMethod(request.getMethodName(), request.getParameterTypes());

                Object rt = method.invoke(service, request.getParameters());

                return RpcResponse.ok(rt);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                log.error("Service(" + request.getClassName() + ") Processing Failed!!", e);
                return RpcResponse.fail("处理请求失败，请重试");
            }
        }
    }
}
