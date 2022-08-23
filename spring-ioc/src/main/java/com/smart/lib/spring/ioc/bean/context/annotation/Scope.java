package com.smart.lib.spring.ioc.bean.context.annotation;

import java.lang.annotation.*;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/23 14:38
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {

    String value() default "singleton";
}
