package com.customized.libs.formatter.autoconfigure;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enable Data Formatter注解开启Formatter
 *
 * @author yan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(FormatterAutoConfiguration.class)
public @interface EnableDataFormatter {
}
