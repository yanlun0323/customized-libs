package com.customized.multiple.versions.adapter.autoconfigure;

import com.customized.multiple.versions.adapter.autoconfigure.web.servlet.config.annotation.MultipleVersionDelegatingWebMvcConfiguration;
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
@Import(MultipleVersionDelegatingWebMvcConfiguration.class)
public @interface EnableMultipleVersions {
}
