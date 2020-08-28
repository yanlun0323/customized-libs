package com.customized.log.core;

import org.apache.logging.log4j.ThreadContext;

/**
 * @author yan
 */
public class Context {

    public final static String TRACEID = "_traceid";

    private static final ThreadLocal<Request> REQUEST_LOCAL = new ThreadLocal<Request>();

    public static void initialLocal(Request request) {
        if (null == request) {
            return;
        }
        // log4j上线稳重添加traceId
        ThreadContext.put(TRACEID, request.getTraceId());
        REQUEST_LOCAL.set(request);
    }

    public static Request getCurrentRequest() {
        return REQUEST_LOCAL.get();
    }
}