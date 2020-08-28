package com.customized.log.trace.dubbo.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.customized.log.core.Context;
import com.customized.log.core.Request;

/**
 * @author yan
 */
@Activate(group = Constants.CONSUMER)
public class LogTraceConsumerFilter extends AbstractDubboFilter implements Filter {

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Request request = super.buildRequestWithConsumer(RpcContext.getContext());
        Context.initialLocal(request);

        return invoker.invoke(invocation);
    }
}
