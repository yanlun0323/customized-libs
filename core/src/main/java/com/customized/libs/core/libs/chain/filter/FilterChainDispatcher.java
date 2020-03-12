package com.customized.libs.core.libs.chain.filter;

import com.customized.libs.core.libs.chain.filter.impl.Filter001Impl;
import com.customized.libs.core.libs.chain.filter.impl.Filter002Impl;

import java.util.ArrayList;
import java.util.List;

public class FilterChainDispatcher {

    /**
     * 责任链模式 -> 后进先出（堆栈）
     *
     * @param args
     */
    public static void main(String[] args) {
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter001Impl());
        filters.add(new Filter002Impl());
        FilterChainImpl chain = new FilterChainImpl(filters);
        chain.doChain("User Chain !!");
    }
}
