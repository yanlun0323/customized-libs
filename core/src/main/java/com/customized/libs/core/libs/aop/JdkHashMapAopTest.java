package com.customized.libs.core.libs.aop;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/3/2 10:28
 */
public class JdkHashMapAopTest {

    public static void main(String[] args) {
        JdkRegexpMethodPointcut regexpMethodPointcut = new JdkRegexpMethodPointcut();
        regexpMethodPointcut.setPattern(".*put.*");

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(regexpMethodPointcut);
        advisor.setAdvice(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.printf("当前存放的key为 %s，值为 %s\r\n", args[0], args[1]);
                args[1] = String.format("%s:%s", args[0], args[1]);
                System.out.println("val changed to ==> " + args[1]);
            }
        });

        // 构建代理对象
        ProxyFactory proxyFactory = new ProxyFactory(new HashMap<String, String>(16));
        proxyFactory.addAdvisors(advisor);

        @SuppressWarnings("unchecked")
        Map<String, String> proxyMap = (Map<String, String>) proxyFactory.getProxy();
        proxyMap.put("User", "Yan");

        System.out.println(proxyMap);
    }
}
