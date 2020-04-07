package com.customized.libs.formatter.autoconfigure;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author yan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(FormatterAutoConfiguration.class)
public @interface EnableDataFormatter {
}
