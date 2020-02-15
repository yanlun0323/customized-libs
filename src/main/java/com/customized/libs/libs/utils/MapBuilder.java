package com.customized.libs.libs.utils;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder {

    private Map<String, Object> params;

    private MapBuilder() {
        this.params = new HashMap<>();
    }

    public MapBuilder add(String key, Object value) {
        if (key == null) return this;
        if (value == null) {
            this.params.remove(key);
        } else {
            this.params.put(key, value);
        }
        return this;
    }

    public MapBuilder add(Map<String, Object> values) {
        if (values != null && values.size() > 0) {
            for (Map.Entry<String, Object> entry : values.entrySet()) {
                add(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    public MapBuilder remove(String key) {
        if (key != null) this.params.remove(key);
        return this;
    }

    public Map<String, Object> get() {
        return this.params;
    }

    public static MapBuilder create() {
        MapBuilder builder = new MapBuilder();
        return builder;
    }

    public static MapBuilder create(String key, Object value) {
        MapBuilder builder = new MapBuilder();
        builder.add(key, value);
        return builder;
    }

    public static MapBuilder create(Map<String, Object> values) {
        MapBuilder builder = new MapBuilder();
        builder.add(values);
        return builder;
    }

}
