package com.customized.multiple.versions.adapter.autoconfigure;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enable Multiple Version Ctrl Auto Configuration
 *
 * @author yan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MultipleVersionCtrlAutoConfiguration.class)
public @interface EnableMultipleVersions {
}
