package com.customized.libs.core.libs.spring.handler;

import com.customized.libs.core.exception.CommonException;
import com.customized.libs.core.libs.spring.handler.core.Handler;
import com.customized.libs.core.libs.spring.handler.core.SQLHandlerFactory;
import com.customized.libs.core.libs.spring.handler.core.UpdateHandler;
import com.customized.libs.core.libs.spring.handler.enums.SQLType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author yan
 */
@Component
public class SQLHandlerTest implements ApplicationContextAware {

    private ApplicationContext context;

    @Autowired
    private SQLHandlerFactory handlerFactory;

    /**
     * 在实际应用的地方，方案一更能贴合实际，根据handler类型来定义，语义上更清晰表达了目的
     * 方案二，直接通过具体的业务类class定义业务操作类型，感觉有点乱，不直观
     *
     * @throws CommonException
     */
    @PostConstruct
    public void test() throws CommonException {
        // 方案一
        Handler handler = this.handlerFactory.get(SQLType.UPDATE);
        handler.execute();

        // 方案二
        Handler handler0 = this.context.getBean(UpdateHandler.class);
        handler0.execute();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
