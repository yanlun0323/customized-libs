package com.customized.rpc.client;

import com.customized.rpc.core.protocol.RpcRequest;
import com.customized.rpc.core.protocol.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcProxyFactory<T> implements InvocationHandler {

    private static Logger log = LoggerFactory.getLogger(RpcProxyFactory.class);

    private Class<T> clazz;

    private RpcClient client;

    public RpcProxyFactory(RpcClient client, Class<T> clazz) {
        this.client = client;
        this.clazz = clazz;
    }

    @SuppressWarnings("unchecked")
    public T getProxyObject() {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Object公共方法处理
        if (Object.class == method.getDeclaringClass()) {
            String name = method.getName();
            if ("equals".equals(name)) {
                return proxy == args[0];
            } else if ("hashCode".equals(name)) {
                return System.identityHashCode(proxy);
            } else if ("toStirng".equals(name)) {
                return proxy.getClass().getName()
                        + "@" + Integer.toHexString(System.identityHashCode(proxy))
                        + ", with InvocationHandler " + this;
            } else {
                throw new IllegalStateException(String.valueOf(method));
            }
        }

        // 请求参数封装
        RpcRequest request = new RpcRequest();
        request.setClassName(clazz.getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);

        try {
            // 发起网络请求，并接收响应
            RpcResponse response = client.sendRequest(request);
            if (response.getStatus() == 1) {
                log.info("==> Rpc Invoke Success!!");
                return response.getData();
            }
            log.info("==> Rpc Invoke Failed({})!!", response.getError());
            throw new Exception(response.getError());
        } catch (Exception ex) {
            log.error("==> Rpc Invoke Error", ex);
            throw new RuntimeException(ex);
        }
    }
}
