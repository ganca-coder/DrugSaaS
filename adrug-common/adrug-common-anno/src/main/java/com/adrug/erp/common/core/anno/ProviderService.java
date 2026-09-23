package com.adrug.erp.common.core.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO
 *
 * @author 甘成安
 * @date 2026/9/18 12:04
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProviderService {
    Class<?> provider();

    String method();

    Class[] args() default {};
}
