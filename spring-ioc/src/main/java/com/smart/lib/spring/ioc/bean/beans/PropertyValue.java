package com.smart.lib.spring.ioc.bean.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yan
 * @version 1.0
 * @description 属性注入对象
 * @date 2022/8/15 15:11
 */
@Data
@AllArgsConstructor
public class PropertyValue {

    private String name;
    private Object value;
}
