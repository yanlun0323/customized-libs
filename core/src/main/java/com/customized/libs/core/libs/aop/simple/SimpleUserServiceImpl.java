package com.customized.libs.core.libs.aop.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/2/23 11:31
 */
@Service
@Slf4j
public class SimpleUserServiceImpl {

    /**
     * PostConstruct是启动前加载，此时未织入代理，无法完成SimpleAopConfig内的切入
     */
    @PostConstruct
    public void hello() {
        System.out.println("Hello Simple Service Impl");
        log.info("Hello Simple Service Impl");
    }
}
