package com.smart.lib.spring.ioc.bean.stereotype;

import java.lang.annotation.*;

/**
 * 仅支持class定义
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    String value() default "";
}
