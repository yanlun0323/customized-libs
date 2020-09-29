package com.customized.libs.core.libs.spring.handler.core;

import com.customized.libs.core.exception.CommonErrCode;
import com.customized.libs.core.exception.CommonException;
import com.customized.libs.core.libs.spring.handler.enums.SQLType;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author yan
 */
@Component
@Slf4j
public class SQLHandlerFactory {

    private static final Map<SQLType, Handler> CONTAINER = Maps.newConcurrentMap();

    @Autowired
    private void setHandler(List<Handler> handlers) {
        handlers.forEach(c -> CONTAINER.putIfAbsent(c.getType(), c));
    }

    public Handler get(SQLType type) throws CommonException {
        Handler handler = CONTAINER.get(type);
        if (Objects.isNull(handler)) {
            log.error("==> Handler({}) Not Exists!!", type.getType());
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        return handler;
    }
}
