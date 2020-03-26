package com.customized.libs.core.libs.spring.env;

import com.google.common.collect.Maps;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yan
 */
@Component
public class RefreshPropertySource extends MapPropertySource {

    public RefreshPropertySource(String name, Map<String, Object> source) {
        super(name, source);
    }

    public RefreshPropertySource() {
        super("RefreshPropertySource", Maps.newHashMap());
    }

    public void refresh() {
        String env = "beta";
        this.source.put("env", env);
    }
}
