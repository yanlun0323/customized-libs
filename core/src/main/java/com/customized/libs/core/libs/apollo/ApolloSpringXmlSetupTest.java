package com.customized.libs.core.libs.apollo;

import org.springframework.context.support.GenericXmlApplicationContext;

public class ApolloSpringXmlSetupTest {

    public static void main(String[] args) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext("classpath:spring-apollo-config.xml");
    }
}
