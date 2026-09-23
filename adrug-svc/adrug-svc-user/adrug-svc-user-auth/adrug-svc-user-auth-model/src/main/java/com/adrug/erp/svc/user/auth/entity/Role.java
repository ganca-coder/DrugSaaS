package com.adrug.erp.svc.user.auth.entity;

import com.adrug.erp.common.entity.BaseEntity;
import com.adrug.erp.common.enums.DataScopeEnum;
import com.adrug.erp.svc.user.auth.enums.RoleStatusEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色实体（对应通用库 role 表）。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("role")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 角色编码 */
    private String roleCode;

    /** 角色名称 */
    private String roleName;

    /** 状态（{@link RoleStatusEnum}） */
    private RoleStatusEnum status;

    /** 数据范围（{@link DataScopeEnum}） */
    private DataScopeEnum dataScope;

    /** 备注 */
    private String remark;
}
