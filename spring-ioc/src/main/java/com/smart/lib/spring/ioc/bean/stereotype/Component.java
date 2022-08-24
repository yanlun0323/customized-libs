package com.smart.lib.spring.ioc.bean.stereotype;

import java.lang.annotation.*;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/16 13:49
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     *
     * @return the suggested component name, if any (or empty String otherwise)
     */
    String value() default "";
}
