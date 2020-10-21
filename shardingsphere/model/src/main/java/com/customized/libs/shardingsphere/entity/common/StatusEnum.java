package com.customized.libs.shardingsphere.entity.common;

public enum StatusEnum {

    /**
     * task-通用状态标记
     */
    NORMAL(1, "正常"),

    DISABLE(2, "停用");

    private Integer code;

    private String desc;

    StatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
