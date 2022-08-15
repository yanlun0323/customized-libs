package com.customized.libs.core.framework.rpc.annotation;

import java.lang.annotation.*;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/6/15 09:57
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SPI {

    /**
     * default extension name
     */
    String value() default "";

}
