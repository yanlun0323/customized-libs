package com.customized.libs.libs.chain.filter.impl;

import com.customized.libs.libs.chain.filter.Filter;
import com.customized.libs.libs.chain.filter.FilterChainImpl;


public class Filter002Impl implements Filter {

    @Override
    public void doFilter(FilterChainImpl chain, String data) {
        chain.doChain(data);
        System.out.println("Filter 002 Data ==> " + data);
    }
}
