package com.customized.libs.core.libs.spring.components.scan;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.ControllerAdviceBean;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过实现ApplicationContextAware获取applicationContext（核心）对象
 *
 * @author yan
 */
@Component
@Slf4j
public class ControllerAdviseLoader implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @PostConstruct
    public void setup() {
        List<ControllerAdviceBean> beans = ControllerAdviseLoader.findAnnotatedBeans(applicationContext);
        log.warn("==> Controller Advice Beans: {}", JSON.toJSONString(beans));
    }

    public static List<ControllerAdviceBean> findAnnotatedBeans(ApplicationContext applicationContext) {
        List<ControllerAdviceBean> beans = new ArrayList<>();
        for (String name : BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext, Object.class)) {
            if (applicationContext.findAnnotationOnBean(name, ControllerAdvice.class) != null) {
                beans.add(new ControllerAdviceBean(name, applicationContext));
            }
        }
        return beans;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
