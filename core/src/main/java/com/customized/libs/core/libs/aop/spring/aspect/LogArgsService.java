package com.customized.libs.core.libs.aop.spring.aspect;


import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author yan
 */
@Slf4j
public class LogArgsService implements MethodBeforeAdvice {


    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        log.info("准备执行方法：" + method.getName() + "，参数列表：" + Arrays.toString(args));
    }
}
