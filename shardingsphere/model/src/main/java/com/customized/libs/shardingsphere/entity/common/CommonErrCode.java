package com.customized.libs.shardingsphere.entity.common;

/**
 * @author yan
 */
public enum CommonErrCode {

    /**
     * 通用ErrorCode定义
     */
    NONE("000000", ""),

    LOGIN_INVALID("300000", "登录失效"),

    ARGS_INVALID("400000", "请求参数有误"),

    AUTH_TOKEN_INVALID("400300", "访问令牌非法"),

    NO_PERMISSION("400310", "没有访问权限"),

    SIGNATURE_INVALID("400320", "签名校验错误"),

    REPEAT_REQUEST("400330", "请求拒绝，重复请求"),

    NO_DATA_FOUND("400400", "没有数据"),

    NETWORK_ERROR("400500", "网络通讯故障"),

    BUSINESS("400600", "业务处理异常"),

    SERVICE_NOT_EXISTS("420000", "服务不存在"),

    SERVICE_UNAVAILABLE("420001", "服务不可用"),

    INTERNAL_SERVER_ERROR("500000", "服务器内部错误"),

    SERVICE_INVOKE_ERROR("500100", "服务调用出错"),

    DB_ERROR("500200", "数据库错误"),

    UNKNOW_ERROR("999999", "网络超时或未知异常");

    String code;
    String desc;

    CommonErrCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
