package com.customized.libs.core.libs.aop.spring.annotation.aspect;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/3/3 11:14
 */
@Service
public class SomethingServiceImpl implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 可以看到通过method0调用method1时，aop没有生效。直接调用method1时，aop才会生效。
     * <p>
     * 事务方法自调用失效就是因为这个原因，因为调用的不是代理对象的方法
     */
    public void method0() {
        System.out.println("execute method0");
        System.out.println("method0 invoke method1");
        // this.method1()调用不会走代理，因为this是当前对象，而不是代理对象
        // this.method1();
        // 通过AopContext获取当前的代理对象
        // 若标记@Aspect的类未加入Spring容器，则会报以下错误
        // Cannot find current proxy: Set 'exposeProxy' property on Advised to 'true' to make it available.
        SomethingServiceImpl proxy = (SomethingServiceImpl) AopContext.currentProxy();
        System.out.println("\r\n方案一调用method1: （(SomethingServiceImpl) AopContext.currentProxy()）");
        proxy.method1();

        System.out.println("\r\n方案二调用method1: （this.applicationContext.getBean(SomethingServiceImpl.class)）");
        SomethingServiceImpl bean = this.applicationContext.getBean(SomethingServiceImpl.class);
        bean.method1();
    }

    public void method1() {
        System.out.println("execute method1");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
