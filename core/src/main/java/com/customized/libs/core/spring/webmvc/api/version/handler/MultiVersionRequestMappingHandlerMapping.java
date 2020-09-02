package com.customized.libs.core.spring.webmvc.api.version.handler;

import com.customized.libs.core.spring.webmvc.api.version.annotations.Version;
import com.customized.libs.core.spring.webmvc.api.version.discover.ApiVersionCodeDiscoverer;
import com.customized.libs.core.spring.webmvc.api.version.util.VersionsUtil;
import com.googlecode.aviator.AviatorEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Custom RequestMappingHandlerMapping for support multi-version of spring mvc restful api with same url.
 * Version code provide by {@code ApiVersionCodeDiscoverer}.
 * <p>
 * <p>
 * How to use ?
 * <p>
 * Spring mvc config case:
 * <p>
 * <pre class="code">
 *
 * @author yan
 * @Configuration public class WebConfig extends WebMvcConfigurationSupport {
 * @Override protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
 * MultiVersionRequestMappingHandlerMapping requestMappingHandlerMapping = new MultiVersionRequestMappingHandlerMapping();
 * requestMappingHandlerMapping.registerApiVersionCodeDiscoverer(new HeaderApiVersionCodeDiscoverer());
 * return requestMappingHandlerMapping;
 * }
 * }</pre>
 * <p>
 * Controller/action case:
 * <p>
 * <pre class="code">
 * @RestController
 * @RequestMapping(value = "/api/product")
 * public class ProductController {
 * @RequestMapping(value = "detail", method = GET)
 * public something detailDefault(int id) {
 * return something;
 * }
 * @RequestMapping(value = "detail", method = GET)
 * @ApiVersion(value = 1.1)
 * public something detailV11(int id) {
 * return something;
 * }
 * @RequestMapping(value = "detail", method = GET)
 * @ApiVersion(value = 1.2)
 * public something detailV12(int id) {
 * return something;
 * }
 * }</pre>
 * <p>
 * Client case:
 * <p>
 * <pre class="code">
 * $.ajax({
 * type: "GET",
 * url: "http://www.xxx.com/api/product/detail?id=100",
 * headers: {
 * value: 1.1
 * },
 * success: function(data){
 * do something
 * }
 * });</pre>
 * @since 2017-07-07
 */
public class MultiVersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private static final Logger logger = LoggerFactory.getLogger(MultiVersionRequestMappingHandlerMapping.class);

    private final static Map<String, Map<String, HandlerMethod>> HANDLER_METHOD_MAP = new HashMap<>(128);

    /**
     * key pattern,such as：/api/product/detail[GET]@1.1
     */
    private final static String HANDLER_METHOD_KEY_PATTERN = "%s[%s]@%s";

    private List<ApiVersionCodeDiscoverer> apiVersionCodeDiscoverers = new ArrayList<>(8);

    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        Version version = method.getAnnotation(Version.class);
        if (version != null) {
            registerMultiVersionApiHandlerMethod(handler, method, mapping, version);
            return;
        }
        super.registerHandlerMethod(handler, method, mapping);
    }

    @Override
    protected HandlerMethod lookupHandlerMethod(String lookupPath, HttpServletRequest request) throws Exception {
        HandlerMethod restApiHandlerMethod = lookupMultiVersionApiHandlerMethod(lookupPath, request);
        if (restApiHandlerMethod != null) {
            return restApiHandlerMethod;
        }
        return super.lookupHandlerMethod(lookupPath, request);
    }

    public void registerApiVersionCodeDiscoverer(ApiVersionCodeDiscoverer apiVersionCodeDiscoverer) {
        if (apiVersionCodeDiscoverers.contains(apiVersionCodeDiscoverer)) {
            return;
        }
        apiVersionCodeDiscoverers.add(apiVersionCodeDiscoverer);
    }

    private void registerMultiVersionApiHandlerMethod(Object handler, Method method, RequestMappingInfo mapping, Version version) {
        PatternsRequestCondition patternsCondition = mapping.getPatternsCondition();
        RequestMethodsRequestCondition methodsCondition = mapping.getMethodsCondition();
        if (patternsCondition.getPatterns().size() == 0
                || methodsCondition.getMethods().size() == 0) {
            return;
        }
        Iterator<String> patternIterator = patternsCondition.getPatterns().iterator();
        Iterator<RequestMethod> methodIterator = methodsCondition.getMethods().iterator();
        while (patternIterator.hasNext() && methodIterator.hasNext()) {
            String patternItem = patternIterator.next();
            RequestMethod methodItem = methodIterator.next();
            String key = String.format(HANDLER_METHOD_KEY_PATTERN, patternItem, methodItem.name(), "DEFAULT");

            Map<String, HandlerMethod> handlerMethods = HANDLER_METHOD_MAP
                    .getOrDefault(key, new HashMap<>(16));
            HandlerMethod handlerMethod = super.createHandlerMethod(handler, method);
            handlerMethods.putIfAbsent(version.value(), handlerMethod);

            HANDLER_METHOD_MAP.putIfAbsent(key, handlerMethods);
            if (logger.isDebugEnabled()) {
                logger.debug("register ApiVersion HandlerMethod of {} {}", key, handlerMethod);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private HandlerMethod lookupMultiVersionApiHandlerMethod(String lookupPath, HttpServletRequest request) {
        String version = tryResolveApiVersion(request);
        if (StringUtils.hasText(version)) {
            String key = String.format(HANDLER_METHOD_KEY_PATTERN, lookupPath, request.getMethod(), "DEFAULT");
            Map<String, HandlerMethod> handlerMethods = HANDLER_METHOD_MAP.get(key);
            if (handlerMethods != null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("lookup ApiVersion HandlerMethod of {} {}", key, handlerMethods);
                }
                Map<String, Object> env = new HashMap<>(16);
                env.put("version", VersionsUtil.convertVersion(version));

                return Objects.requireNonNull(handlerMethods.entrySet().stream()
                        .filter(m -> (boolean) AviatorEvaluator.execute(m.getKey(), env))
                        .findFirst().orElse(null))
                        .getValue();
            }
            logger.debug("lookup ApiVersion HandlerMethod of {} failed", key);
        }
        return null;
    }

    private String tryResolveApiVersion(HttpServletRequest request) {
        for (ApiVersionCodeDiscoverer apiVersionCodeDiscoverer : apiVersionCodeDiscoverers) {
            String versionCode = apiVersionCodeDiscoverer.getVersionCode(request);
            if (StringUtils.hasText(versionCode)) {
                return versionCode;
            }
        }
        return null;
    }
}