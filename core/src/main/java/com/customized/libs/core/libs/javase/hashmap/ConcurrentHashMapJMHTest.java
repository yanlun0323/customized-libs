package com.customized.libs.core.libs.javase.hashmap;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class ConcurrentHashMapJMHTest {
    private static final int TEN_MILLION = 10000000;

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void jdk8ConcurrentHashMap() {
        Map<String, Object> data = new ConcurrentHashMap<>();
        for (int i = 0; i < TEN_MILLION; i++) {
            data.put(String.valueOf(i), i);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void jdk7ConcurrentHashMap() {
        Map<String, Object> data = new com.customized.libs.core.libs.javase.hashmap.ConcurrentHashMap<>();
        for (int i = 0; i < TEN_MILLION; i++) {
            data.put(String.valueOf(i), i);
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ConcurrentHashMapJMHTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
