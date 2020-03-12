package com.customized.libs.core.libs.chain.filter;

import java.util.List;


public class FilterChainImpl {

    private Integer index = 0;

    private final int filterSize;

    private List<Filter> filters;

    public FilterChainImpl(List<Filter> filters) {
        this.filterSize = filters.size();
        this.filters = filters;
    }

    public void doChain(String data) {
        if (this.index < this.filterSize) {
            this.getNext().doFilter(this, data);
        }
    }

    private Filter getNext() {
        return this.filters.get(this.index++);
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }
}
