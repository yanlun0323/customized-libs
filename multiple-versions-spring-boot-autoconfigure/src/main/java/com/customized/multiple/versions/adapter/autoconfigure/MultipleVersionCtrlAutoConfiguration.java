package com.customized.multiple.versions.adapter.autoconfigure;

import com.customized.multiple.versions.adapter.autoconfigure.constants.MultipleVersionsConstants;
import com.customized.multiple.versions.adapter.discover.ApiVersionCodeDiscoverer;
import com.customized.multiple.versions.adapter.discover.impl.DefaultApiVersionCodeDiscoverer;
import com.customized.multiple.versions.adapter.discover.impl.HeaderApiVersionCodeDiscoverer;
import com.customized.multiple.versions.adapter.handler.MultiVersionRequestMappingHandlerMapping;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.logging.Logger;

/**
 * AutoConfiguration标记Configuration/EnableConfigurationProperties
 * <p>
 * EnableConfigurationProperties配合标记ConfigurationProperties的Properties可动态注入属性数据
 *
 * @author yan
 */
@Configuration
@EnableConfigurationProperties(MultipleVersionProperties.class)
@ConditionalOnProperty(
        prefix = MultipleVersionsConstants.VERSIONS_PROPERTIES_PREFIX,
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class MultipleVersionCtrlAutoConfiguration extends WebMvcConfigurationSupport {

    private static Logger logger = Logger.getLogger(MultipleVersionCtrlAutoConfiguration.class.getSimpleName());

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
        handlerMapping.registerApiVersionCodeDiscoverer(super.getApplicationContext()
                .getBean(ApiVersionCodeDiscoverer.class));
        return handlerMapping;
    }

    @Bean
    @ConditionalOnProperty(
            prefix = MultipleVersionsConstants.VERSIONS_PROPERTIES_PREFIX,
            name = MultipleVersionsConstants.VERSION_CODE_DISCOVERER_TYPE,
            havingValue = MultipleVersionsConstants.VERSION_CODE_DISCOVERER_DEFAULT,
            matchIfMissing = true
    )
    public ApiVersionCodeDiscoverer defaultApiVersionCodeDiscoverer() {
        logger.warning("==> Select Default Version Code Discoverer!!!");
        return new DefaultApiVersionCodeDiscoverer();
    }


    @Bean
    @ConditionalOnProperty(
            prefix = MultipleVersionsConstants.VERSIONS_PROPERTIES_PREFIX,
            name = MultipleVersionsConstants.VERSION_CODE_DISCOVERER_TYPE,
            havingValue = MultipleVersionsConstants.VERSION_CODE_DISCOVERER_HEADER
    )
    public ApiVersionCodeDiscoverer headerApiVersionCodeDiscoverer() {
        logger.warning("==> Select Header Version Code Discoverer!!!");
        return new HeaderApiVersionCodeDiscoverer();
    }
}
