package com.customized.libs.mvc.factory;

import com.customized.libs.mvc.annotations.MvcController;
import com.customized.libs.mvc.annotations.MvcMapping;
import com.customized.libs.mvc.bean.MvcBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yan
 */
@Component
public class MvcBeanFactory implements InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;

    private Map<String, MvcBean> beans = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        this.loadBeans();
    }

    public MvcBean getBean(String key) {
        return this.beans.get(key);
    }

    private void loadBeans() {
        beans.clear();
        Map<String, Object> targetBeans = this.applicationContext.
                getBeansWithAnnotation(MvcController.class);

        targetBeans.forEach((key, value) -> {
            Class<?> type = value.getClass();
            for (Method mtd : type.getDeclaredMethods()) {
                MvcMapping mp = mtd.getAnnotation(MvcMapping.class);
                if (mp != null) {
                    MvcBean bean = this.buildBean(type, mtd, mp);
                    beans.put(mp.name(), bean);
                }
            }
        });
    }

    private MvcBean buildBean(Class<?> type, Method mtd, MvcMapping mp) {
        MvcBean bean = new MvcBean();
        bean.setContext(applicationContext);
        bean.setPath(mp.name());
        bean.setTargetMethod(mtd);
        bean.setContentType(mp.contentType());
        bean.setTargetClass(type);
        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
