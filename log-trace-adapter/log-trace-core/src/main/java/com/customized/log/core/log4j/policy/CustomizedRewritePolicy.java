package com.customized.log.core.log4j.policy;

import com.customized.log.core.Context;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.ContextDataFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.util.KeyValuePair;
import org.apache.logging.log4j.util.StringMap;

import java.util.HashMap;
import java.util.Map;

@Plugin(name = "CustomizedRewritePolicy", category = Core.CATEGORY_NAME, elementType = "rewritePolicy", printObject = true)
public final class CustomizedRewritePolicy implements RewritePolicy {

    private CustomizedRewritePolicy() {

    }

    /**
     * Creates a policy to rewrite levels for a given logger name.
     *
     * @param loggerNamePrefix The logger name prefix for events to rewrite; all event logger names that start with this string will be
     *                         rewritten.
     * @param levelPairs       The levels to rewrite, the key is the source level, the value the target level.
     * @return a new LoggerNameLevelRewritePolicy
     */
    @PluginFactory
    public static CustomizedRewritePolicy createPolicy(
            // @formatter:off
            @PluginAttribute("logger") final String loggerNamePrefix,
            @PluginElement("KeyValuePair") final KeyValuePair[] levelPairs) {
        // @formatter:on
        return new CustomizedRewritePolicy();
    }

    @SuppressWarnings("all")
    public LogEvent rewrite(final LogEvent source) {
        StringMap contextData = (StringMap) source.getContextData();
        HashMap<String, String> contextMap = Maps.newHashMap(contextData.toMap());
        contextMap.put(Context.TRACE_ID, contextMap.containsKey(Context.TRACE_ID)
                ? contextMap.get(Context.TRACE_ID) : "");

        contextData = ContextDataFactory.createContextData(); // replace with new instance
        if (contextMap != null) {
            for (final Map.Entry<String, String> entry : contextMap.entrySet()) {
                contextData.putValue(entry.getKey(), entry.getValue());
            }
        }
        return new Log4jLogEvent.Builder(source).setContextData(contextData).build();
    }
}