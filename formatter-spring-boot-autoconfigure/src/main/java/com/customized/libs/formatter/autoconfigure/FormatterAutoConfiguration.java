package com.customized.libs.formatter.autoconfigure;

import com.customized.libs.formatter.autoconfigure.service.DataFormatterService;
import com.customized.libs.formatter.autoconfigure.service.DefaultDataFormatterService;
import com.customized.libs.formatter.autoconfigure.service.FastJSONDataFormatterService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

/**
 * AutoConfiguration标记Configuration/EnableConfigurationProperties
 * <p>
 * EnableConfigurationProperties配合标记ConfigurationProperties的Properties可动态注入属性数据
 *
 * @author yan
 */
@Configuration
@EnableConfigurationProperties(FormatterProperties.class)
public class FormatterAutoConfiguration {

    private static Logger logger = Logger.getLogger(FormatterAutoConfiguration.class.getSimpleName());

    /**
     * ConditionalOnClass可根据是否引入了对应的JAR来启用不同的格式化服务
     * Data Formatter Builder
     */
    @Bean
    @ConditionalOnClass(name = "com.alibaba.fastjson.JSON")
    public DataFormatterService fastJsonFormatter(FormatterProperties properties) {
        logger.warning(">>> Formatter Properties ==> " + properties);
        return new FastJSONDataFormatterService();
    }

    /**
     * Data Formatter Builder
     */
    @Bean
    @ConditionalOnMissingClass(value = "com.alibaba.fastjson.JSON")
    public DataFormatterService defaultFormatter(FormatterProperties properties) {
        logger.warning(">>> Formatter Properties ==> " + properties);
        return new DefaultDataFormatterService();
    }
}
