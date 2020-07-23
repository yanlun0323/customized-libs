package com.customized.libs.core.libs.rpc.test;

import com.customized.libs.core.libs.rpc.RpcClient;
import com.customized.libs.core.libs.rpc.RpcServer;
import com.customized.libs.core.libs.rpc.netty.base.NettyRpcClientImpl;
import com.customized.libs.core.libs.rpc.netty.base.NettyRpcServerImpl;
import com.customized.libs.core.libs.rpc.socket.base.SocketRpcClientImpl;
import com.customized.libs.core.libs.rpc.socket.base.SocketRpcServerImpl;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class RpcJMHTest {
    private static final int TEN_MILLION = 10;

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void socketRpcTest() {
        for (int i = 0; i < TEN_MILLION; i++) {

            RpcClient client = new SocketRpcClientImpl();
            client.connect(new InetSocketAddress("127.0.0.1", SOCKET_PORT));

            Tinterface tinterface = (Tinterface) client.getInvoker(Tinterface.class);

            IntStream.range(0, 5).forEach(c -> {
                tinterface.send("Rpc ==> Data " + c);
            });
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void nettyRpcTest() {
        for (int i = 0; i < TEN_MILLION; i++) {
            RpcClient client = new NettyRpcClientImpl();
            client.connect(new InetSocketAddress("127.0.0.1", NETTY_PORT));

            Tinterface tinterface = (Tinterface) client.getInvoker(Tinterface.class);

            IntStream.range(0, 5).forEach(c -> {
                tinterface.send("Rpc ==> Data " + c);
            });
        }
    }


    private static final Integer SOCKET_PORT = 9905;

    @SuppressWarnings("all")
    private static void initSocketRpcServer() {
        new Thread(() -> {
            RpcServer rpcServer = new SocketRpcServerImpl();
            rpcServer.export(Tinterface.class, TinterfaceImpl.class);
            rpcServer.open(SOCKET_PORT);
        }).start();
    }


    private static final Integer NETTY_PORT = 9918;

    @SuppressWarnings("all")
    private static void initNettyRpcServer() {
        new Thread(() -> {
            RpcServer rpcServer = new NettyRpcServerImpl();
            rpcServer.export(Tinterface.class, TinterfaceImpl.class);
            rpcServer.open(NETTY_PORT);
        }).start();
    }

    public static void main(String[] args) throws RunnerException {
        initSocketRpcServer();
        initNettyRpcServer();

        // 休眠-确保Server成功开启监听服务
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Options opt = new OptionsBuilder()
                .include(RpcJMHTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
