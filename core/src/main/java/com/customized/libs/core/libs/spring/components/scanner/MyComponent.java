package com.customized.libs.core.libs.spring.components.scanner;

import java.lang.annotation.*;

/**
 * @author yan
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyComponent {

}
