package com.adrug.erp.svc.user.auth.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 授权中心登录入参（由应用层编排组装）。
 * <p>
 * 包含账号凭证（密文）与角色/权限，授权中心仅做密码匹配与 JWT 签发。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class AuthLoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 登录账号 */
    private String username;

    /** 密码（明文） */
    private String password;

    /** 密码密文（BCrypt，来自账号） */
    private String passwordHash;

    private Long accountId;

    private Long tenantId;

    private Long orgId;

    private Long employeeId;

    private Integer accountType;

    /** 账号状态（1-启用，0-停用） */
    private Integer status;

    /** 角色编码列表 */
    private List<String> roles;

    /** 权限标识列表 */
    private List<String> permissions;

    /** 数据范围（1-全部，2-自定义，3-本机构及下级，4-本机构，5-仅本人） */
    private Integer dataScope;

    /** 数据范围机构 ID（已解析，供数据查询过滤） */
    private List<Long> dataScopeOrgIds;
}
