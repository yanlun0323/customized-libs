package com.customized.libs.core.libs.rpc.socket.base;

import com.customized.libs.core.libs.rpc.RpcClient;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

@Slf4j
public class SocketRpcClientImpl<T> implements RpcClient<T> {

    private static InetSocketAddress SOCKET_ADDRESS;

    @Override
    public void connect(InetSocketAddress socketAddress) {
        SOCKET_ADDRESS = socketAddress;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getInvoker(Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                Socket socket = null;
                ObjectOutputStream out = null;
                ObjectInputStream input = null;
                try {
                    socket = new Socket();
                    socket.connect(SOCKET_ADDRESS);

                    // service + method + parameterTypes + args
                    out = new ObjectOutputStream(socket.getOutputStream());
                    out.writeUTF(service.getSimpleName());
                    out.writeUTF(method.getName());
                    out.writeObject(method.getParameterTypes());
                    out.writeObject(args);

                    input = new ObjectInputStream(socket.getInputStream());
                    return input.readObject();
                } catch (Exception e) {
                    log.error("==> getInvoker Failed!", e);
                } finally {
                    try {
                        if (out != null) out.close();
                        if (input != null) input.close();
                        if (socket != null) socket.close();
                    } catch (Exception ex) {
                        log.error("Close Stream Failed!", ex);
                    }
                }
                return null;
            }
        });
    }
}
