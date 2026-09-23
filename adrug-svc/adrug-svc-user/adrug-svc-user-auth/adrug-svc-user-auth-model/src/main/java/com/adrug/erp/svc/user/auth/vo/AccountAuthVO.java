package com.adrug.erp.svc.user.auth.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 账号授权信息（登录时用于签发 JWT）。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class AccountAuthVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 角色编码列表 */
    private List<String> roles;

    /** 权限标识列表 */
    private List<String> permissions;

    /** 数据范围（1-全部，2-自定义，3-本机构及下级，4-本机构，5-仅本人） */
    private Integer dataScope;

    /** 数据范围机构 ID（自定义时角色绑定的机构；其余范围由应用层按 orgId 解析） */
    private List<Long> dataScopeOrgIds;
}
