package com.adrug.erp.svc.user.info.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 账号凭证视图对象（含密码密文，仅供登录编排使用，不对外暴露）。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class AccountCredentialVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    /** 密码（BCrypt 密文） */
    private String password;

    private Long tenantId;

    private Long orgId;

    private Long employeeId;

    private Integer accountType;

    private Integer status;
}
