package com.smart.lib.spring.ioc.bean.annotations;

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
}
