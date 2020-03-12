package com.customized.libs.core.libs.chain.filter;

public interface Filter {

    void doFilter(FilterChainImpl chain, String data);
}
