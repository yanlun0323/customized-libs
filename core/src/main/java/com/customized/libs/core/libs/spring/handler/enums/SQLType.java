package com.customized.libs.core.libs.spring.handler.enums;

public enum SQLType {
    /**
     * SQL类型定义
     */
    INSERT("Insert", "新增"),
    UPDATE("Update", "更新"),
    SELECT("Select", "查询");


    String type;
    String desc;

    SQLType(String code, String desc) {
        this.type = code;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
