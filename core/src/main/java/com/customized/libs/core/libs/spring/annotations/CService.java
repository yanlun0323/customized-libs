package com.customized.libs.core.libs.spring.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation派生
 *
 * @author yan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface CService {


    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     *
     * @return the suggested component name, if any (or empty String otherwise)
     */
    @AliasFor(annotation = Component.class, attribute = "value")
    String value() default "";

    /**
     * 为什么这里再次定义Component@Value可以用CValue更改容器中bean name？
     *
     * @return
     */
    @AliasFor(annotation = Component.class, attribute = "value")
    String cValue() default "";
}
