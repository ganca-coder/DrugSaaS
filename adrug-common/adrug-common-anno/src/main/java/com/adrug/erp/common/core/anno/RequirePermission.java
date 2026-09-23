package com.adrug.erp.common.core.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限校验注解。
 * <p>
 * 标注在 Controller/Service 方法上，表示调用需具备指定权限标识；
 * 由 {@code PermissionAspect} 校验当前登录用户的权限（含 {@code *} / {@code *:*} 通配）。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequirePermission {

    /**
     * 所需权限标识，如 {@code system:role:delete}。
     */
    String value();
}
