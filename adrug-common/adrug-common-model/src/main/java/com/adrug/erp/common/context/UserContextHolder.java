package com.adrug.erp.common.context;

/**
 * 登录用户上下文持有器（ThreadLocal）。
 * <p>
 * 与 {@link TenantContextHolder} 配合：租户 ID 单独存，其余身份信息（账号/机构/角色/权限）存此处。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public final class UserContextHolder {

    /** 账号 ID 请求头 */
    public static final String ACCOUNT_ID_HEADER = "X-Account-Id";
    /** 机构 ID 请求头 */
    public static final String ORG_ID_HEADER = "X-Org-Id";
    /** 用户名请求头 */
    public static final String USERNAME_HEADER = "X-Username";
    /** 角色请求头（预留，逗号分隔） */
    public static final String ROLES_HEADER = "X-Roles";
    /** 权限请求头（预留，逗号分隔） */
    public static final String PERMISSIONS_HEADER = "X-Permissions";
    /** 数据范围请求头 */
    public static final String DATA_SCOPE_HEADER = "X-Data-Scope";
    /** 数据范围机构请求头（逗号分隔） */
    public static final String DATA_SCOPE_ORGS_HEADER = "X-Data-Scope-Orgs";

    private static final ThreadLocal<UserContext> CONTEXT = new ThreadLocal<>();

    private UserContextHolder() {
    }

    public static void set(UserContext context) {
        CONTEXT.set(context);
    }

    public static UserContext get() {
        return CONTEXT.get();
    }

    public static Long getAccountId() {
        UserContext context = CONTEXT.get();
        return context == null ? null : context.getAccountId();
    }

    public static Long getOrgId() {
        UserContext context = CONTEXT.get();
        return context == null ? null : context.getOrgId();
    }

    public static Integer getDataScope() {
        UserContext context = CONTEXT.get();
        return context == null ? null : context.getDataScope();
    }

    public static java.util.List<Long> getDataScopeOrgIds() {
        UserContext context = CONTEXT.get();
        return context == null ? null : context.getDataScopeOrgIds();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
