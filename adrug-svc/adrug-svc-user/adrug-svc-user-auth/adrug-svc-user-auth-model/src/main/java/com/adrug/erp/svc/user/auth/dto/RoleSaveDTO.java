package com.adrug.erp.svc.user.auth.dto;

import com.adrug.erp.common.enums.DataScopeEnum;
import com.adrug.erp.svc.user.auth.enums.RoleStatusEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色新增/变更入参。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class RoleSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键（变更时必填） */
    private Long id;

    /** 角色编码 */
    private String roleCode;

    /** 角色名称 */
    private String roleName;

    /** 状态，对应枚举类 {@link RoleStatusEnum} */
    private Integer status;

    /** 数据范围，对应枚举类 {@link DataScopeEnum} */
    private Integer dataScope;

    /** 备注 */
    private String remark;
}
