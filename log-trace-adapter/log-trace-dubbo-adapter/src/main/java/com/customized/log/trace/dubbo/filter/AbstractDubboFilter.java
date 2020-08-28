package com.customized.log.trace.dubbo.filter;

import com.alibaba.dubbo.rpc.RpcContext;
import com.customized.log.core.Request;
import com.customized.log.core.TraceIdGenerator;

import static com.customized.log.core.Context.TRACE_ID;

abstract class AbstractDubboFilter {

    protected Request buildRequestWithConsumer(RpcContext rpcContext) {
        Request request = new Request();
        // 新建全局traceId
        request.setTraceId(TraceIdGenerator.getTraceId());
        rpcContext.setAttachment(TRACE_ID, request.getTraceId());
        return request;
    }

    protected Request buildRequestWithProvider(RpcContext rpcContext) {
        Request request = new Request();
        request.setTraceId(rpcContext.getAttachment(TRACE_ID));
        return request;
    }
}
