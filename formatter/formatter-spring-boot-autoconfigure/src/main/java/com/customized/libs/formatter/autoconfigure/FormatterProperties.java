package com.customized.libs.formatter.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 引入Spring-boot-configuration-processor后，编译完成后会根据以下注释自动生成spring-configuration-metadata.json
 * {
 * "groups": [{
 * "name": "formatter",
 * "type": "com.customized.libs.formatter.autoconfigure.FormatterProperties",
 * "sourceType": "com.customized.libs.formatter.autoconfigure.FormatterProperties"
 * }],
 * "properties": [{
 * "name": "formatter.enabled",
 * "type": "java.lang.Boolean",
 * "description": "Formatter Switch Production is set to true, development set to false",
 * "sourceType": "com.customized.libs.formatter.autoconfigure.FormatterProperties"
 * },
 * {
 * "name": "formatter.type",
 * "type": "java.lang.String",
 * "description": "Formatter type",
 * "sourceType": "com.customized.libs.formatter.autoconfigure.FormatterProperties"
 * }
 * ],
 * "hints": []
 * }
 *
 * @author yan
 */
@ConfigurationProperties(prefix = "formatter")
public class FormatterProperties {

    /**
     * Formatter Switch Production is set to true, development set to false
     */
    private Boolean enabled;

    /**
     * Formatter type
     */
    private String type;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "FormatterProperties{" +
                "enabled=" + enabled +
                ", type='" + type + '\'' +
                '}';
    }
}
