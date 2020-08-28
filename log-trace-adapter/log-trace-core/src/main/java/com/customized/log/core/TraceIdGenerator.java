package com.customized.log.core;

import java.util.UUID;

public class TraceIdGenerator {

    public static String getTraceId() {
        return UUID.randomUUID().toString();
    }
}
