package com.customized.libs.springboot.config;

import com.mongodb.MongoClientURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/**
 * @author yan
 */
@Configuration
public class MongoConfig {

    private static final Logger logger = LoggerFactory.getLogger(MongoConfig.class);

    @Bean(name = "defaultMongoFactory")
    public MongoDbFactory mongoFactory(
            @Value("${spring.data.mongodb.oss-uri}") String connectionString) {
        logger.debug("<<>> OSSMongoFactory(" + connectionString + ") Initialized\r\n");
        return new SimpleMongoDbFactory(new MongoClientURI(connectionString));
    }

    @Bean(name = "defaultMongoTemplate")
    public MongoTemplate mongoTemplate(
            @Qualifier("defaultMongoFactory") MongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }

    @Bean(name = "defaultGridFsOperations")
    public GridFsOperations settlementGridFsOperations(
            @Qualifier("defaultMongoFactory") MongoDbFactory mongoDbFactory,
            @Qualifier("defaultMongoTemplate") MongoTemplate mongoTemplate) {
        return new GridFsTemplate(mongoDbFactory, mongoTemplate.getConverter());
    }
}
