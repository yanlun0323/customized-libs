package com.smart.lib.spring.ioc.bean.test;

import com.smart.lib.spring.ioc.bean.beans.PropertyValue;
import com.smart.lib.spring.ioc.bean.beans.PropertyValues;
import com.smart.lib.spring.ioc.bean.factory.DefaultListableBeanFactory;
import com.smart.lib.spring.ioc.bean.factory.config.BeanDefinition;
import com.smart.lib.spring.ioc.bean.factory.config.BeanReference;

import java.util.Map;

/**
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/15 10:50
 */
public class UserServiceImplTest {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 注册UserDao
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDaoImpl.class));

        // 注册UserService
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("userDao", (BeanReference) () -> "userDao"));
        propertyValues.addPropertyValue(new PropertyValue("name", "CODER"));
        BeanDefinition beanDefinition = new BeanDefinition(UserServiceImpl.class, propertyValues);
        beanFactory.registerBeanDefinition("UserServiceImpl", beanDefinition);

        // getBean
        UserServiceImpl service = beanFactory.getBean(UserServiceImpl.class);
        service.hello();
        Map<String, Object> allUser = service.getAllUser();
        System.out.println("All users ==> " + allUser);
    }
}
