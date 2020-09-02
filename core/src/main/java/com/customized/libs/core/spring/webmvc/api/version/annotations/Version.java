package com.customized.libs.core.spring.webmvc.api.version.annotations;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * @author yan
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface Version {

    /**
     * 版本比表达式
     *
     * @return
     */
    String value() default "";
}
