package com.customized.libs.shardingsphere;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author yan
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
@EnableTransactionManagement
@ComponentScan("com.customized.libs.shardingsphere")
@MapperScan("com.customized.libs.shardingsphere.dao")
public class ShardingshpereApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingshpereApplication.class, args);
    }
}
