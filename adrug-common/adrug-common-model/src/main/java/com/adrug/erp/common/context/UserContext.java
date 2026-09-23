package com.adrug.erp.common.context;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 登录用户上下文（由网关解析 JWT 后经请求头透传，过滤器组装）。
 * <p>
 * 角色 / 权限为预留字段，后续接入 RBAC 后填充。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class UserContext implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 账号 ID */
    private Long accountId;

    /** 租户 ID */
    private Long tenantId;

    /** 机构 ID */
    private Long orgId;

    /** 登录账号 */
    private String username;

    /** 角色（预留） */
    private List<String> roles;

    /** 权限（预留） */
    private List<String> permissions;

    /** 数据范围（1-全部，2-自定义，3-本机构及下级，4-本机构，5-仅本人） */
    private Integer dataScope;

    /** 数据范围机构 ID */
    private List<Long> dataScopeOrgIds;
}
