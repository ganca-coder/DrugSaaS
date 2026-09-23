package com.adrug.erp.common.context;

/**
 * 租户上下文持有器。
 * <p>
 * 通过 ThreadLocal 在单次请求内传递租户 ID：由 {@code TenantContextFilter} 从请求头
 * {@value #HEADER} 读取后写入，请求结束清理；数据源自动填充（MetaObjectHandler）与
 * 服务端数据隔离查询均从此处取租户 ID。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public final class TenantContextHolder {

    /** 租户 ID 请求头名称 */
    public static final String HEADER = "X-Tenant-Id";

    /** 默认租户 ID（默认菜单） */
    public static final long DEFAULT_TENANT_ID = 0L;

    private static final ThreadLocal<Long> TENANT_ID = new ThreadLocal<>();

    private TenantContextHolder() {
    }

    /**
     * 写入当前线程租户 ID。
     */
    public static void set(Long tenantId) {
        TENANT_ID.set(tenantId);
    }

    /**
     * 获取当前线程租户 ID，未设置时返回默认租户 ID（0）。
     */
    public static Long get() {
        Long tenantId = TENANT_ID.get();
        return tenantId == null ? DEFAULT_TENANT_ID : tenantId;
    }

    /**
     * 清理当前线程租户 ID（请求结束时调用）。
     */
    public static void clear() {
        TENANT_ID.remove();
    }
}
