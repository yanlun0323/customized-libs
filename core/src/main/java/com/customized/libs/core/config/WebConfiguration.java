package com.customized.libs.core.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.SentinelWebMvcConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.customized.libs.core.interceptor.ResponseResultInterceptor;
import com.customized.libs.core.mvc.servlet.DispatcherServlet;
import com.customized.log.spring.webmvc.GlobalLogTraceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import javax.servlet.Servlet;

/**
 * TODO dubbo启动，则不需要@ImportResouce导入Spring-MVC.xml配置
 *
 * @author yan
 */
@Configuration
@ImportResource("classpath:spring-mvc.xml")
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private ApplicationContext context;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        SentinelWebMvcConfig config = new SentinelWebMvcConfig();
        // Enable the HTTP method prefix.
        config.setHttpMethodSpecify(true);
        // Add to the interceptor list.
        registry.addInterceptor(new SentinelWebInterceptor(config)).addPathPatterns("/**");

        registry.addInterceptor(new GlobalLogTraceInterceptor()).addPathPatterns("/**");

        registry.addInterceptor(new ResponseResultInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public FilterRegistrationBean webFilter() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        registration.setFilter(characterEncodingFilter);
        return registration;
    }

    /**
     * Servlet dispatcherServletMvc mapped to [/dispatcher/*]<br/>
     * Servlet dispatcherServlet mapped to [/]<br/>
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean webServlet() {
        ServletRegistrationBean<Servlet> registrationBean =
                new ServletRegistrationBean<>(new DispatcherServlet(context), "/dispatcher/*");

        /**
         * 其中需要注意的是registration.setName("rest")，这个语句很重要，因为name相同的ServletRegistrationBean只有一个会生效，
         * 也就是说，后注册的会覆盖掉name相同的ServletRegistrationBean。
         * 如果不指定，默认为“dispatcherServlet”而spring boot提供的DispatcherServlet的name就是“dispatcherServlet”。
         * 可以在spring boot的DispatcherServletAutoConfiguration类中找到。
         *
         * 作者：JerryL_
         * 链接：https://www.jianshu.com/p/be2dafc8c644
         * 来源：简书
         * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
         */
        registrationBean.setName("dispatcherServletMvc");
        return registrationBean;
    }

    @Bean
    public HttpMessageConverters messageConverters() {
        FastJsonHttpMessageConverter fastjson = new FastJsonHttpMessageConverter();
        FormHttpMessageConverter httpMessageConverter = new FormHttpMessageConverter();
        return new HttpMessageConverters(fastjson, httpMessageConverter);
    }

    /**
     * 解决：DEBUG org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor - Could not find default TaskScheduler bean
     * org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'org.springframework.scheduling.TaskScheduler' available
     *
     * @return
     */
    @Bean
    public TaskScheduler scheduledExecutorService() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(8);
        scheduler.setThreadNamePrefix("scheduled-thread-");
        return scheduler;
    }
}
