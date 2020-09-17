package com.customized.libs.core.libs.dubbo.filter;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.customized.libs.core.libs.dubbo.invoker.CInvoker;

public class C0FilterImpl implements CFilter {

    @Override
    public Result invoke(CInvoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println("########### Filter C0 Do Something!! ###########");
        System.out.println();
        return invoker.invoke(invocation);
    }
}
