package com.adrug.erp.svc.user.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色-数据机构关联实体（对应通用库 role_data_org 表）。
 * <p>
 * 仅当角色数据范围为「自定义」时使用。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("role_data_org")
public class RoleDataOrg {

    /** 主键（雪花 ID） */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 角色 ID */
    private Long roleId;

    /** 机构 ID */
    private Long orgId;
}
