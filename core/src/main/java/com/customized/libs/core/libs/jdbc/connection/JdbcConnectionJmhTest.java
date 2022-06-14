package com.customized.libs.core.libs.jdbc.connection;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/6/14 09:46
 */

@BenchmarkMode({Mode.Throughput})
// @BenchmarkMode(Mode.AverageTime) // 测试完成时间
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS) // 预热 1 轮，每次 1s
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS) // 测试 5 轮，每次 3s
@Fork(1) // fork 1 个线程
@State(Scope.Benchmark)
@Threads(200) // 开启 1000 个并发线程
public class JdbcConnectionJmhTest {

    private static final Map PROPERTIES0 = new HashMap();
    private static final Map PROPERTIES1 = new HashMap();
    public static DataSource DATASOURCE0;
    public static DataSource DATASOURCE1;

    private static JdbcTemplate JDBC0;
    private static JdbcTemplate JDBC1;


    static {
        try {
            PROPERTIES0.put("driverClassName", "com.mysql.cj.jdbc.Driver");
            PROPERTIES0.put("password", "qJwP6NZ1dFx!QXTL");
            PROPERTIES0.put("url", "jdbc:mysql://127.0.0.1:3306/diego?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8");
            PROPERTIES0.put("username", "root");
            PROPERTIES0.put("initialSize", "8");
            PROPERTIES0.put("maxActive", "128");
            PROPERTIES0.put("minIdle", "8");

            PROPERTIES1.putAll(PROPERTIES0);
            PROPERTIES1.put("maxActive", "8");

            DATASOURCE0 = DruidDataSourceFactory.createDataSource(PROPERTIES0);
            DATASOURCE1 = DruidDataSourceFactory.createDataSource(PROPERTIES1);

            JDBC0 = new JdbcTemplate(DATASOURCE0);
            JDBC1 = new JdbcTemplate(DATASOURCE1);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) throws RunnerException {
        // 启动基准测试
        Options opt = new OptionsBuilder()
                .include(JdbcConnectionJmhTest.class.getSimpleName()) // 要导入的测试类
                .build();
        new Runner(opt).run(); // 执行测试
    }

    @Benchmark
    public void jdbc0(Blackhole blackhole) {
        blackhole.consume(JDBC0.queryForList("select * from t_diego_quickpay_order"));
    }

    @Benchmark
    public void jdbc1(Blackhole blackhole) {
        blackhole.consume(JDBC1.queryForList("select * from t_diego_quickpay_order"));
    }
}
