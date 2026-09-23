package com.adrug.erp.svc.user.info.entity;

import com.adrug.erp.common.entity.IdBaseEntity;
import com.adrug.erp.svc.user.info.enums.OrgStatusEnum;
import com.adrug.erp.svc.user.info.enums.OrgTypeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 机构实体（药店/门店，树形），对应通用库 org 表。
 * <p>
 * org 表为租户级数据（含 tenant_id，无 org_id，自身即机构），故继承 {@link IdBaseEntity} 并自持 tenantId。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("org")
public class Org extends IdBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 租户 ID（真实租户 ID，数据隔离） */
    private Long tenantId;

    /** 上级机构 ID（0-顶级） */
    private Long parentId;

    /** 机构编码 */
    private String orgCode;

    /** 机构名称 */
    private String orgName;

    /** 机构类型（{@link OrgTypeEnum}） */
    private OrgTypeEnum orgType;

    /** 状态（{@link OrgStatusEnum}） */
    private OrgStatusEnum status;

    /** 备注 */
    private String remark;
}
