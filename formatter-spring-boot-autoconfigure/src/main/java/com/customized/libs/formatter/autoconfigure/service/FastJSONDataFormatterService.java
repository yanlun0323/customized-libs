package com.customized.libs.formatter.autoconfigure.service;

import com.alibaba.fastjson.JSON;

public class FastJSONDataFormatterService implements DataFormatterService {

    @Override
    public String format(Object data) {
        return JSON.toJSONString(data);
    }
}
