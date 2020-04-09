package com.customized.libs.core.libs.spring.components.scanner;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 注解派生 + AliasFor别名注解
 *
 * @author yan
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@MyComponentScan
public @interface EnableMyComponentScan {

    /**
     * 别名注解-属性传递
     *
     * @return
     */
    @AliasFor(annotation = MyComponentScan.class, attribute = "basePackages")
    String[] scanBasePackages() default {};

    @AliasFor(annotation = MyComponentScan.class, attribute = "basePackageClasses")
    Class<?>[] scanBasePackageClasses() default {};
}
