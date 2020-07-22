package com.customized.libs.core.libs.rpc.socket.base;

import com.alibaba.fastjson.JSON;
import com.customized.libs.core.libs.rpc.RpcServer;
import com.customized.libs.core.utils.threadpool.CallerRunsPolicyWithReport;
import com.customized.libs.core.utils.threadpool.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

@Slf4j
public class SocketRpcServerImpl implements RpcServer {

    public static ExecutorService SOCKET_RPC_EXECUTORS = new ThreadPoolExecutor(8, 128, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1024),
            new NamedThreadFactory("Socket-Rpc"),
            new CallerRunsPolicyWithReport("Socket-Rpc")
    );

    private static final Map<String, Class> SERVICE_REGISTER = new ConcurrentHashMap<>(128);


    @Override
    public void export(Class service, Class impl) {
        SERVICE_REGISTER.put(service.getSimpleName(), impl);
    }

    @Override
    public void open(int port) {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);

            log.warn("==> Socket-Rpc Server Starting...");

            log.warn("==> Service Register {}", JSON.toJSONString(SERVICE_REGISTER));

            // 启动监听
            while (true) {
                this.doInvokeListener(socket.accept());
            }
        } catch (Exception ex) {
            log.error("==> Open Socket Port " + port + " Failed!!", ex);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    log.error("==> Socket Close Failed!!", e);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void doInvokeListener(Socket client) {
        SOCKET_RPC_EXECUTORS.submit(() -> {
            ObjectInputStream ois = null;
            ObjectOutputStream oos = null;

            // service + method + parameterTypes + args
            try {
                ois = new ObjectInputStream(client.getInputStream());
                String serviceName = ois.readUTF();
                String methodName = ois.readUTF();
                Class<?>[] parameterTypes = (Class<?>[]) ois.readObject();
                Object[] arguments = (Object[]) ois.readObject();
                Class target = SERVICE_REGISTER.get(serviceName);
                if (Objects.isNull(target)) {
                    throw new ClassNotFoundException("Class " + serviceName + " Not Found!");
                }

                Method method = target.getMethod(methodName, parameterTypes);
                Object result = method.invoke(target.newInstance(), arguments);

                oos = new ObjectOutputStream(client.getOutputStream());
                oos.writeObject(result);
            } catch (IOException e) {
                log.error("==> Socket Accept Failed!", e);
            } catch (ClassNotFoundException e) {
                log.error("==> Class Not Found", e);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (ois != null) {
                        ois.close();
                    }
                    if (client != null) {
                        client.close();
                    }
                    if (oos != null) {
                        oos.close();
                    }
                } catch (IOException ex) {
                    log.error("==> Stream Close Failed!", ex);
                }
            }
        });
    }
}
