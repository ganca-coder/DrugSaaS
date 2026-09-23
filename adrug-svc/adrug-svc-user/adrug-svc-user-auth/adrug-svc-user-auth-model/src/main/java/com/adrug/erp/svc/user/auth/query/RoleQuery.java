package com.adrug.erp.svc.user.auth.query;

import com.adrug.erp.common.dto.PageQuery;
import com.adrug.erp.common.enums.DataScopeEnum;
import com.adrug.erp.svc.user.auth.enums.RoleStatusEnum;
import lombok.Data;

/**
 * 角色查询条件。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class RoleQuery extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 关键字：角色名称 / 编码 模糊匹配 */
    private String keyword;

    /** 主键 */
    private Long id;

    /** 租户 ID（服务端从请求头上下文注入） */
    private Long tenantId;

    /** 角色编码（精确匹配） */
    private String roleCode;

    /** 角色名称（精确匹配） */
    private String roleName;

    /** 状态，对应枚举类 {@link RoleStatusEnum} */
    private Integer status;

    /** 数据范围，对应枚举类 {@link DataScopeEnum} */
    private Integer dataScope;
}
