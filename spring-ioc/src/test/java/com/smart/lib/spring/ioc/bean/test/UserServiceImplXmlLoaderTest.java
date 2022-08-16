package com.smart.lib.spring.ioc.bean.test;

import com.alibaba.fastjson.JSON;
import com.smart.lib.spring.ioc.bean.annotations.Component;
import com.smart.lib.spring.ioc.bean.factory.DefaultListableBeanFactory;
import com.smart.lib.spring.ioc.bean.factory.support.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * @author yan
 * @version 1.0
 * @description 基于xml读取bean配置文件
 * @date 2022/8/16 10:26
 */
public class UserServiceImplXmlLoaderTest {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:spring-beans.xml");

        UserServiceImpl service = beanFactory.getBean(UserServiceImpl.class);
        service.hello();
        Map<String, Object> allUser = service.getAllUser();
        System.out.println("All users ==> " + allUser);

        String[] beanNamesForAnnotation = beanFactory.getBeanNamesForAnnotation(Component.class);
        System.out.println(JSON.toJSONString(beanNamesForAnnotation));

        Map<String, Object> beansWithAnnotation = beanFactory.getBeansWithAnnotation(Component.class);
        System.out.println(JSON.toJSONString(beansWithAnnotation, true));

        Map<String, UserServiceImpl> beansOfType = beanFactory.getBeansOfType(UserServiceImpl.class);
        System.out.println(JSON.toJSONString(beansOfType, true));
    }
}
