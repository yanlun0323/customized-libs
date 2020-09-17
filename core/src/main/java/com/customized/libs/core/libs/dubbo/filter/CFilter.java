package com.customized.libs.core.libs.dubbo.filter;

import com.alibaba.dubbo.common.extension.SPI;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.customized.libs.core.libs.dubbo.invoker.CInvoker;

@SPI
public interface CFilter {

    Result invoke(CInvoker<?> invoker, Invocation invocation) throws RpcException;
}
