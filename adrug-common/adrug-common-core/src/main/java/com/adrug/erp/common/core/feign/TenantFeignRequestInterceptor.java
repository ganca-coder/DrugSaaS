package com.adrug.erp.common.core.feign;

import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.context.UserContext;
import com.adrug.erp.common.context.UserContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Feign 身份请求头透传。
 * <p>
 * 将当前线程上下文的身份信息（租户/账号/机构/用户名/角色/权限/数据范围）写入请求头，随 Feign 调用传递到下游服务。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Component
public class TenantFeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header(TenantContextHolder.HEADER, String.valueOf(TenantContextHolder.get()));
        UserContext context = UserContextHolder.get();
        if (context == null) {
            return;
        }
        if (context.getAccountId() != null) {
            template.header(UserContextHolder.ACCOUNT_ID_HEADER, String.valueOf(context.getAccountId()));
        }
        if (context.getOrgId() != null) {
            template.header(UserContextHolder.ORG_ID_HEADER, String.valueOf(context.getOrgId()));
        }
        if (context.getUsername() != null) {
            template.header(UserContextHolder.USERNAME_HEADER, context.getUsername());
        }
        if (context.getDataScope() != null) {
            template.header(UserContextHolder.DATA_SCOPE_HEADER, String.valueOf(context.getDataScope()));
        }
        if (context.getDataScopeOrgIds() != null && !context.getDataScopeOrgIds().isEmpty()) {
            template.header(UserContextHolder.DATA_SCOPE_ORGS_HEADER,
                    context.getDataScopeOrgIds().stream().map(String::valueOf).collect(Collectors.joining(",")));
        }
        if (context.getRoles() != null && !context.getRoles().isEmpty()) {
            template.header(UserContextHolder.ROLES_HEADER, String.join(",", context.getRoles()));
        }
        if (context.getPermissions() != null && !context.getPermissions().isEmpty()) {
            template.header(UserContextHolder.PERMISSIONS_HEADER, String.join(",", context.getPermissions()));
        }
    }
}
