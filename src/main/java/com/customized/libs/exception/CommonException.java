package com.customized.libs.exception;

import lombok.Data;

/**
 * @author yan
 */
@Data
public class CommonException extends Exception {

    private static final long serialVersionUID = 5936483490346218078L;
    private String errCode;

    private String errMsg;

    public CommonException(CommonErrCode errCode, String desc) {
        super(desc);
        this.errCode = errCode.getCode();
        this.errMsg = desc;
    }

    public CommonException(String errCode, String desc) {
        super(desc);
        this.errCode = errCode;
        this.errMsg = desc;
    }

    public CommonException(CommonErrCode errCode) {
        super(errCode.getDesc());
        this.errCode = errCode.getCode();
        this.errMsg = errCode.getDesc();
    }
}

