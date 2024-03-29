/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.customized.libs.provider.dubbo;

import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallback;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.SentinelRpcException;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Eric Zhao
 */
public class CustomizedDubboFallback implements DubboFallback {

    private static Logger logger = LoggerFactory.getLogger(CustomizedDubboFallback.class);

    @Override

    public Result handle(Invoker<?> invoker, Invocation invocation, BlockException ex) {
        logger.error("<<< 方法[" + invocation.getMethodName() + "]调用被限流/降级/系统保护", ex);

        throw new SentinelRpcException(ex);
    }
}
