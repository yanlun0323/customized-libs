package com.customized.libs.core.libs.spring.handler.core;

import com.customized.libs.core.libs.spring.handler.enums.SQLType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SelectHandler extends AbstractHandler {

    @Override
    public void execute() {
        log.info("==> Handler Select execute!!");
    }

    @Override
    public SQLType getType() {
        return SQLType.SELECT;
    }
}
