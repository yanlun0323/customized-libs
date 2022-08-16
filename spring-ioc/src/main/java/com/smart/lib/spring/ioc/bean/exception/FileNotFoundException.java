package com.smart.lib.spring.ioc.bean.exception;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/16 09:49
 */
public class FileNotFoundException extends RuntimeException {


    private static final long serialVersionUID = 1948976488757197289L;

    public FileNotFoundException(String msg) {
        super(msg);
    }
}
