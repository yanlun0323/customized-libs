package com.customized.libs.exception;

public class InfException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public InfException() {
        super();
    }

    /**
     * 自定义异常
     *
     * @param msg
     */
    public InfException(String key, String msg, String requestId) {
        super(key + "|" + msg + "|" + requestId);
    }

}
