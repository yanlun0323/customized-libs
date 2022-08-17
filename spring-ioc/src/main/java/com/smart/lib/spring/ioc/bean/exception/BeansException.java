package com.smart.lib.spring.ioc.bean.exception;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/15 10:55
 */
public class BeansException extends RuntimeException {

    private static final long serialVersionUID = -6937938663825994296L;

    public BeansException(String errorMsg) {
        super(errorMsg);
    }
}
