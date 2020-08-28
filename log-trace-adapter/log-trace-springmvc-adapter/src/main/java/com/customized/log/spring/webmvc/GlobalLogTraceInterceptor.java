package com.customized.log.spring.webmvc;

import com.customized.log.core.Context;
import com.customized.log.core.Request;
import com.customized.log.core.TraceIdGenerator;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yan
 */
public class GlobalLogTraceInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse
            , Object handler) {
        // 初始化全局Context容器
        Request request = initRequest(httpServletRequest);

        Context.initialLocal(request);

        return Boolean.TRUE;
    }

    private Request initRequest(HttpServletRequest servletRequest) {
        Request request = new Request();
        // 新建全局traceId
        request.setTraceId(TraceIdGenerator.getTraceId());
        request.setServletRequest(servletRequest);

        return request;
    }
}
