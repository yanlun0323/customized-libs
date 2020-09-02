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

    /**
     * 重写RequestMappingHandlerMapping，支持API多版本处理
     *
     * @return
     */
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return super.requestMappingHandlerMapping();
    }

    /**
     * Protected method for plugging in a custom subclass of
     * {@link RequestMappingHandlerMapping}.
     *
     * @since 4.0
     */
    @Override
    protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        MultiVersionRequestMappingHandlerMapping handlerMapping = new MultiVersionRequestMappingHandlerMapping();
        handlerMapping.registerApiVersionCodeDiscoverer(new DefaultApiVersionCodeDiscoverer());
        return handlerMapping;
    }
}
