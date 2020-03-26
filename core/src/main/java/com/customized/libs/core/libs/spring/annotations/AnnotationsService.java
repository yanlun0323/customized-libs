package com.customized.libs.core.libs.spring.annotations;

import javax.annotation.PostConstruct;

/**
 * @author yan
 */
@CService(cValue = "cAnnotationService")
public class AnnotationsService {

    @PostConstruct
    public void setup() {
        System.out.println("setup");
    }

    public void sayHello() {
        System.out.println("Hello World!!");
    }
}
