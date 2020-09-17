package com.customized.libs.core.libs.dubbo.protocol;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;
import com.alibaba.fastjson.JSON;
import com.customized.libs.core.libs.dubbo.filter.CFilter;
import com.customized.libs.core.libs.dubbo.invoker.CInvoker;

import java.util.List;

/**
 * @author yan
 */
public class CProtocolFilterWrapper {

    /**
     * 1、这里逻辑并不复杂，首先通过ExtensionLoader加载CFilterLoader，然后获取activateExtension找到激活的CFilter；
     * <p>
     * 2、根据链的调用顺序，逆向遍历filter数组，构建last cInvoker对象
     * <p>
     * 3核心代码在invoker方法(filter.invoke(next, invocation))，此处next每一次遍历都会被赋值为last（初始值为方法传递过来的invoker）,根据此方式就构建了过滤链
     *
     * @param invoker
     * @param key
     * @param group
     * @param <T>
     * @return
     */
    private static <T> CInvoker<T> buildInvokerChain(final CInvoker<T> invoker, String key, String group) {
        CInvoker<T> last = invoker;
        List<CFilter> filters = ExtensionLoader.getExtensionLoader(CFilter.class).getActivateExtension(invoker.getUrl(), key, group);
        if (!filters.isEmpty()) {
            for (int i = filters.size() - 1; i >= 0; i--) {
                final CFilter filter = filters.get(i);
                final CInvoker<T> next = last;
                last = new CInvoker<T>() {

                    @Override
                    public URL getUrl() {
                        return invoker.getUrl();
                    }

                    @Override
                    public Result invoke(Invocation invocation) throws RpcException {
                        return filter.invoke(next, invocation);
                    }
                };
            }
        }
        return last;
    }

    public static void main(String[] args) {
        URL url = URL.valueOf("dubbo://xxxx.xxx?filter=filter0,filter1");

        CInvoker<String> zero = new CInvoker<String>() {
            @Override
            public URL getUrl() {
                return url;
            }

            @Override
            public Result invoke(Invocation invocation) throws RpcException {
                RpcResult rt = new RpcResult();
                rt.setValue("CInvoker Result!!");
                return rt;
            }
        };
        CInvoker target = CProtocolFilterWrapper.buildInvokerChain(zero, "filter", "");
        Result rt = target.invoke(null);
        System.out.println(rt);

        System.out.println();

        System.out.println(JSON.toJSONString(target));
    }
}
