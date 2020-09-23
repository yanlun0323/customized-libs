package com.customized.libs.core.libs.dubbo.filter;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.customized.libs.core.libs.dubbo.invoker.CInvoker;

@Activate(order = 1, group = "dubbo-filter")
public class CDefault1FilterImpl implements CFilter {

    @Override
    public Result invoke(CInvoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println("########### Filter Default 01 Do Something!! ###########");
        System.out.println();
        return invoker.invoke(invocation);
    }
}
