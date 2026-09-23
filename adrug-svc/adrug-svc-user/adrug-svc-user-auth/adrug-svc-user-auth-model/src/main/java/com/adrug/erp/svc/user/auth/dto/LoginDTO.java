package com.adrug.erp.svc.user.auth.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录入参。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 登录账号 */
    private String username;

    /** 密码（明文） */
    private String password;
}
