package com.customized.libs.formatter.autoconfigure.service;

/**
 * @author yan
 */
public class DefaultDataFormatterService implements DataFormatterService {

    @Override
    public String format(Object data) {
        return String.valueOf(data);
    }
}
