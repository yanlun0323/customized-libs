package com.customized.libs.core.libs.spring.annotations;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author yan
 */
public class SpringAnnotationTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AnnotationsService.class);
        context.refresh();

        AnnotationsService service = context.getBean("cAnnotationService", AnnotationsService.class);
        service.sayHello();
    }
}
