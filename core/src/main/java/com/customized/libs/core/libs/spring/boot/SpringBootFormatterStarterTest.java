package com.customized.libs.core.libs.spring.boot;

import com.customized.libs.formatter.autoconfigure.EnableDataFormatter;
import com.customized.libs.formatter.autoconfigure.service.DataFormatterService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yan
 */
@SpringBootApplication
@EnableDataFormatter
public class SpringBootFormatterStarterTest {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootFormatterStarterTest.class);
        DataFormatterService dataFormatterService = context.getBean(DataFormatterService.class);

        Map<String, Object> data = new HashMap<>(16);
        data.put("type", "JSON");
        String formatted = dataFormatterService.format(data);

        System.out.println(formatted);
    }
}
