package com.customized.lib.web.interceptor;

import com.blade.ioc.annotation.Bean;
import com.blade.mvc.RouteContext;
import com.blade.mvc.hook.WebHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Bean
public class BladeInterceptor implements WebHook {

    private static Logger log = LoggerFactory.getLogger(BladeInterceptor.class);

    @Override
    public boolean before(RouteContext context) {
        log.warn("==> Into Blade Web Interceptor: {}", context.remoteAddress());
        return true;
    }

    @Override
    public boolean after(RouteContext context) {
        log.warn("==> Leave Blade Web Interceptor: {}", context.method());
        return true;
    }
}
