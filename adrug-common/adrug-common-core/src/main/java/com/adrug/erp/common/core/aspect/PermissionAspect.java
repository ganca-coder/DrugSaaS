package com.adrug.erp.common.core.aspect;

import com.adrug.erp.common.context.UserContext;
import com.adrug.erp.common.context.UserContextHolder;
import com.adrug.erp.common.core.anno.RequirePermission;
import com.adrug.erp.common.core.exception.BusinessException;
import com.adrug.erp.common.result.ResultCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 权限校验切面。
 * <p>
 * 校验当前登录用户（{@link UserContextHolder}）是否具备 {@link RequirePermission#value()}
 * 指定权限；支持 {@code *} / {@code *:*} 通配（超级管理员）。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Aspect
@Component
public class PermissionAspect {

    @Around("@annotation(requirePermission)")
    public Object check(ProceedingJoinPoint pjp, RequirePermission requirePermission) throws Throwable {
        UserContext context = UserContextHolder.get();
        List<String> permissions = (context == null || context.getPermissions() == null)
                ? Collections.emptyList()
                : context.getPermissions();
        String required = requirePermission.value();
        boolean has = permissions.contains("*") || permissions.contains("*:*") || permissions.contains(required);
        if (!has) {
            throw new BusinessException(ResultCode.FORBIDDEN.getCode(), "无权限：" + required);
        }
        return pjp.proceed();
    }
}
