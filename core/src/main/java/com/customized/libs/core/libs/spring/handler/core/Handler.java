package com.customized.libs.core.libs.spring.handler.core;

import com.customized.libs.core.libs.spring.handler.enums.SQLType;

public interface Handler {

    /**
     * 执行逻辑
     */
    void execute();

    /**
     * 处理器类型
     *
     * @return
     */
    SQLType getType();
}
