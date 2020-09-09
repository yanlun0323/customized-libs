package com.customized.libs.core.libs.spring.bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author yan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(BeanPostConfigRegistrar.class)
public @interface EnableBeanPostConfig {

}
