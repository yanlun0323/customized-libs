package com.customized.libs.core.config;

import com.customized.libs.core.spring.webmvc.api.version.discover.impl.DefaultApiVersionCodeDiscoverer;
import com.customized.libs.core.spring.webmvc.api.version.handler.MultiVersionRequestMappingHandlerMapping;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author yan
 */
@Configuration
public class WebRequestMappingHandlerConfiguration extends WebMvcConfigurationSupport {

    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        MultiVersionRequestMappingHandlerMapping requestMappingHandlerMapping = new MultiVersionRequestMappingHandlerMapping();
        requestMappingHandlerMapping.registerApiVersionCodeDiscoverer(new DefaultApiVersionCodeDiscoverer());
        return requestMappingHandlerMapping;

    }
}
