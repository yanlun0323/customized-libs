package com.customized.multiple.versions.adapter.autoconfigure;

import com.customized.multiple.versions.adapter.autoconfigure.constants.MultipleVersionsConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 引入Spring-boot-configuration-processor后，编译完成后会根据以下注释自动生成spring-configuration-metadata.json
 *
 * @author yan
 */
@ConfigurationProperties(prefix = MultipleVersionsConstants.VERSIONS_PROPERTIES_PREFIX)
public class MultipleVersionProperties {

    private String strategy;

    private Boolean enabled;

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
