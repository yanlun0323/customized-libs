package com.customized.libs.libs.chain.filter;

public interface Filter {

    void doFilter(FilterChainImpl chain, String data);
}
