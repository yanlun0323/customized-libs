package com.customized.log.core;

import javax.servlet.http.HttpServletRequest;

public class Request {

    private String traceId;

    private HttpServletRequest servletRequest;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public HttpServletRequest getServletRequest() {
        return servletRequest;
    }

    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }
}
