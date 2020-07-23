package com.customized.libs.core.libs.bigdata;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class FindTopNWordsJMHTest {
    private static final int TEN_MILLION = 1000;

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void v0Test() {
        for (int i = 0; i < TEN_MILLION; i++) {
            FindTop100Words.doGetTopNWords(1000);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void v1Test() {
        for (int i = 0; i < TEN_MILLION; i++) {
            FindTop100WordsV2.doGetTopNWords(1000);
        }
    }

    /**
     * # Run complete. Total time: 00:00:56
     * <p>
     * Benchmark                    Mode  Cnt        Score       Error  Units
     * <p>
     * FindTopNWordsJMHTest.v0Test  avgt    5  3759217.832 ± 75222.611  us/op
     * <p>
     * FindTopNWordsJMHTest.v1Test  avgt    5   728391.427 ± 17325.065  us/op
     *
     * @param args
     * @throws RunnerException
     */
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(FindTopNWordsJMHTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
