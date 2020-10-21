package com.customized.libs.springboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yan
 */
@ConfigurationProperties(prefix = "test")
public class BootProperties {

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
