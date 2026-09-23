package com.adrug.erp.common.core.filter;

import com.adrug.erp.common.context.TenantContextHolder;
import com.adrug.erp.common.context.UserContext;
import com.adrug.erp.common.context.UserContextHolder;
import com.adrug.erp.common.core.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 身份上下文过滤器。
 * <p>
 * 从请求头读取身份信息（由网关解析 JWT 后注入）写入上下文，请求结束清理：
 * 租户/账号/机构/用户名/角色/权限/数据范围。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Component
public class TenantContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            TenantContextHolder.set(parseLong(request.getHeader(TenantContextHolder.HEADER)));
            UserContextHolder.set(buildUserContext(request));
            filterChain.doFilter(request, response);
        } finally {
            TenantContextHolder.clear();
            UserContextHolder.clear();
        }
    }

    private UserContext buildUserContext(HttpServletRequest request) {
        UserContext context = new UserContext();
        context.setAccountId(parseLong(request.getHeader(UserContextHolder.ACCOUNT_ID_HEADER)));
        context.setOrgId(parseLong(request.getHeader(UserContextHolder.ORG_ID_HEADER)));
        context.setUsername(request.getHeader(UserContextHolder.USERNAME_HEADER));
        context.setRoles(split(request.getHeader(UserContextHolder.ROLES_HEADER)));
        context.setPermissions(split(request.getHeader(UserContextHolder.PERMISSIONS_HEADER)));
        context.setDataScope(parseInteger(request.getHeader(UserContextHolder.DATA_SCOPE_HEADER)));
        context.setDataScopeOrgIds(splitLong(request.getHeader(UserContextHolder.DATA_SCOPE_ORGS_HEADER)));
        return context;
    }

    private Long parseLong(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseInteger(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private List<String> split(String value) {
        if (!StringUtils.hasText(value)) {
            return Collections.emptyList();
        }
        return Arrays.asList(value.split(","));
    }

    private List<Long> splitLong(String value) {
        if (!StringUtils.hasText(value)) {
            return Collections.emptyList();
        }
        return Arrays.stream(value.split(","))
                .filter(StringUtils::hasText)
                .map(s -> {
                    try {
                        return Long.valueOf(s);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());
    }
}
