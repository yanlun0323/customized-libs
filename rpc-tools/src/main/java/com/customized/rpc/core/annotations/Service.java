package com.customized.rpc.core.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标记RPC服务，并且声明其接口
 *
 * @author yan
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Service {

    /**
     * 接口名称
     *
     * @return
     */
    Class<?> value();
}
