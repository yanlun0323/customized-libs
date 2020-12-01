package com.customized.libs.springboot.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

/**
 * @author yan
 */
@Configuration
public class AutoRefresh implements ApplicationContextAware, InitializingBean {

    private ApplicationContext context;

    private ThymeleafViewResolver thymeleafViewResolver;

    private TemplateEngine templateEngine;

    /**
     * 此处可改造位按需加载template来执行清理任务
     */
    @Scheduled(fixedDelay = 35000L)
    public void refresh() {
        // this.thymeleafViewResolver.clearCache();
        this.templateEngine.clearTemplateCacheFor("home");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.thymeleafViewResolver = this.context.getBean(ThymeleafViewResolver.class);
        this.templateEngine = this.context.getBean(SpringTemplateEngine.class);
    }
}
