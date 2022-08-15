package com.smart.lib.spring.ioc.bean.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 这两个类的作用就是创建出一个用于传递类中属性信息的类，因为属性可能会有很多，所以还需要定义一个集合包装下。
 *
 * @author yan
 * @version 1.0
 * @description 提供PropertyValue的新增方法，管理PropertyValue
 * @date 2022/8/15 15:11
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue propertyValue) {
        this.propertyValueList.add(propertyValue);
    }

    /**
     * 获取数组
     *
     * @return
     */
    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        return this.propertyValueList.stream()
                .filter(p -> p.getName().equals(propertyName))
                .findFirst().orElse(null);
    }
}
