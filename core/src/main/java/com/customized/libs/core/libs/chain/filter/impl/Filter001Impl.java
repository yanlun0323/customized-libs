package com.customized.libs.core.libs.chain.filter.impl;

import com.customized.libs.core.libs.chain.filter.Filter;
import com.customized.libs.core.libs.chain.filter.FilterChainImpl;


public class Filter001Impl implements Filter {

    @Override
    public void doFilter(FilterChainImpl chain, String data) {
        chain.doChain(data);
        System.out.println("Filter 001 Data ==> " + data);
    }
}
