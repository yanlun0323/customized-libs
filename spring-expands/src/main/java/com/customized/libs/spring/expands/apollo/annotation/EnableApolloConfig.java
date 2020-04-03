package com.customized.libs.spring.expands.apollo.annotation;

import com.ctrip.framework.apollo.core.ConfigConsts;
import com.ctrip.framework.apollo.core.spi.Ordered;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 这里的关键点是在注解上使用了@Import(ApolloConfigRegistrar.class)，从而Spring在处理@EnableApolloConfig时会实例化并调用ApolloConfigRegistrar的方法。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(com.customized.libs.spring.expands.apollo.annotation.ApolloConfigRegistrar.class)
public @interface EnableApolloConfig {
    /**
     * Apollo namespaces to inject configuration into Spring Property Sources.
     */
    String[] value() default {ConfigConsts.NAMESPACE_APPLICATION};

    /**
     * The order of the apollo config, default is {@link Ordered#LOWEST_PRECEDENCE}, which is Integer.MAX_VALUE.
     * If there are properties with the same name in different apollo configs, the apollo config with smaller order wins.
     */
    int order() default Ordered.LOWEST_PRECEDENCE;
}