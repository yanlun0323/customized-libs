package com.customized.libs.shardingsphere;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author yan
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
@EnableTransactionManagement
@EnableScheduling
@MapperScan("com.customized.libs.shardingsphere.dao")
public class ShardingshpereApplication {

    /*
     * 在FastJSON第一次调用前初始化才可靠
     */
    static {
        System.setProperty("fastjson.parser.autoTypeSupport", "true");
    }

    public static void main(String[] args) {
        SpringApplication.run(ShardingshpereApplication.class, args);
    }
}
