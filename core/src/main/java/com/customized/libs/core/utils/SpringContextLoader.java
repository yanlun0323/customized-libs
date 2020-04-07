package com.customized.libs.core.utils;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Objects;

/**
 * @author yan
 */
public abstract class SpringContextLoader {

    private static final ContextWrapper CONTEXT_WRAPPER = new ContextWrapper();

    public static ConfigurableApplicationContext getContext() {
        return CONTEXT_WRAPPER.getContext();
    }

    public static Environment getEnvironment() {
        return CONTEXT_WRAPPER.getEnvironment();
    }


    public static void init(ConfigurableApplicationContext context) {
        CONTEXT_WRAPPER.setContext(context);
        CONTEXT_WRAPPER.setEnvironment(context.getEnvironment());
    }

    public static String getProperty(String key, String defaultValue) {
        return Objects.requireNonNull(CONTEXT_WRAPPER.getEnvironment()
        ).getProperty(key, defaultValue);
    }

    public static String getProperty(String key) {
        return Objects.requireNonNull(CONTEXT_WRAPPER.getEnvironment()
        ).getProperty(key);
    }

    private static class ContextWrapper {

        private Environment environment;
        private ConfigurableApplicationContext context;

        public Environment getEnvironment() {
            return environment;
        }

        public void setEnvironment(Environment environment) {
            this.environment = environment;
        }

        public ConfigurableApplicationContext getContext() {
            return context;
        }

        public void setContext(ConfigurableApplicationContext context) {
            this.context = context;
        }
    }
}
