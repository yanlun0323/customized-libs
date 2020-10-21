package com.customized.libs.springboot.service;

import com.alibaba.fastjson.JSON;
import com.customized.libs.springboot.config.Config;
import com.customized.libs.springboot.properties.BootProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


/**
 * @author yan
 */
@Configuration
@EnableConfigurationProperties(BootProperties.class)
public class PropertiesViewService {

    @Resource
    private Config config;

    @Bean
    public Config newInstance(BootProperties bootProperties) {
        Config cfg = new Config();
        cfg.setPassword(bootProperties.getPassword());
        return cfg;
    }

    @PostConstruct
    public void test() {
        System.err.println(JSON.toJSONString(config, true));
    }
}
