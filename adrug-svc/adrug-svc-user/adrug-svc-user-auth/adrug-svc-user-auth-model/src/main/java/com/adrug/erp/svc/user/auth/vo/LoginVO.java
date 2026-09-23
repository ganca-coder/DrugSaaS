package com.adrug.erp.svc.user.auth.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 登录结果。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class LoginVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** JWT token */
    private String token;

    private Long accountId;

    private String username;

    private Long tenantId;

    private Long orgId;

    private Long employeeId;

    /** 账号类型（1-管理员，2-员工） */
    private Integer accountType;

    /** 角色编码列表 */
    private List<String> roles;

    /** 权限标识列表 */
    private List<String> permissions;
}
