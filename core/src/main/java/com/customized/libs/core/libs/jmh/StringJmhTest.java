package com.customized.libs.core.libs.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/6/14 09:50
 */
@BenchmarkMode(Mode.AverageTime) // 测试完成时间
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS) // 预热 1 轮，每次 1s
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS) // 测试 5 轮，每次 3s
@Fork(1) // fork 1 个线程
@State(Scope.Benchmark)
@Threads(10) // 开启 1000 个并发线程
public class StringJmhTest {

    private static final int MAX_SIZE = 10000;

    public static void main(String[] args) throws RunnerException {
        // 启动基准测试
        Options opt = new OptionsBuilder()
                .include(StringJmhTest.class.getSimpleName()) // 要导入的测试类
                .build();
        new Runner(opt).run(); // 执行测试
    }

    @Benchmark
    public String stringConcat() throws InterruptedException {
        String target = "";
        for (int i = 0; i < MAX_SIZE; i++) {
            target += "A" + i;
        }
        return target;
    }

    @Benchmark
    public String stringBuilder() throws InterruptedException {
        StringBuilder target = new StringBuilder("");
        for (int i = 0; i < MAX_SIZE; i++) {
            target.append("A" + i);
        }
        return target.toString();
    }

    @Benchmark
    public String stringBuffer() throws InterruptedException {
        StringBuffer target = new StringBuffer("");
        for (int i = 0; i < MAX_SIZE; i++) {
            target.append("A" + i);
        }
        return target.toString();
    }
}
