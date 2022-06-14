package com.customized.libs.core.framework.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/6/14 16:15
 */
@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
public class RpcFramework {

    private static ServerSocket SERVER;

    private static Map<String, Invoker> exported = new ConcurrentHashMap<>();

    public static void doOpen(int port) throws IOException {
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port: " + port);
        }
        RpcFramework.SERVER = new ServerSocket(port);
        // 循环监听
        for (; ; ) {
            Socket socket = RpcFramework.SERVER.accept();
            new Thread(() -> {
                try {
                    try (ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {
                        // 解析参数和class
                        String serviceKey = input.readUTF();
                        Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                        Object[] arguments = (Object[]) input.readObject();
                        // 输出流
                        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                        Invoker invoker = exported.get(serviceKey);
                        try {
                            output.writeObject(invoker.invoke(parameterTypes, arguments));
                        } catch (Throwable t) {
                            output.writeObject(t);
                        } finally {
                            output.close();
                        }
                    } finally {
                        socket.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * 暴露服务
     *
     * @param service 服务实现
     */
    public static void export(final Object service, final String method) {
        // 打开socket端口，暴露服务
        if (service == null) {
            throw new IllegalArgumentException("service instance == null");
        }
        System.out.println("Export service " + service.getClass().getName());
        String serviceKey = buildServiceKey(service.getClass().getInterfaces()[0], method);
        exported.put(serviceKey, new Invoker(service, method));
    }

    private static String buildServiceKey(Class<?> serviceClazz, String method) {
        return serviceClazz.getName() + ":" + method;
    }

    /**
     * 引用服务
     *
     * @param interfaceClass 接口类型
     * @param host           服务器主机名
     * @param port           服务器端口
     * @param <T>            接口泛型
     * @return 远程服务
     */
    @SuppressWarnings("unchecked")
    public static <T> T refer(final Class<T> interfaceClass, final String host, final int port) {
        if (interfaceClass == null) {
            throw new IllegalArgumentException("Interface class == null");
        }
        if (!interfaceClass.isInterface()) {
            throw new IllegalArgumentException("The" + interfaceClass.getName() + "must be interface class!");
        }
        if (host == null || host.length() == 0) {
            throw new IllegalArgumentException("Host == null");
        }
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port:" + port);
        }
        System.out.println("Get remote service" + interfaceClass.getName() + "from server " + host + ":" + port);

        //  动态代理对象
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 连接socket
                        try {
                            // 为什么要包装一层？
                            // 此处可根据注册中心的注册列表路由具体服务地址，然后实现端到端的RPC调用
                            try (Socket socket = new Socket(host, port);
                                 ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {
                                // 写入方法/参数类型等，通过socket执行数据写出
                                output.writeUTF(buildServiceKey(interfaceClass, method.getName()));
                                output.writeObject(method.getParameterTypes());
                                output.writeObject(args);
                                // socket通讯，获取输入流
                                try (ObjectInputStream
                                             input = new ObjectInputStream(socket.getInputStream())) {
                                    Object result = input.readObject();
                                    if (result instanceof Throwable) {
                                        throw (Throwable) result;
                                    }
                                    return result;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
    }
}
