package com.adrug.erp.svc.user.info.entity;

import com.adrug.erp.common.entity.IdBaseEntity;
import com.adrug.erp.svc.user.info.enums.TenantStatusEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 租户实体（SaaS 平台级，对应通用库 tenant 表）。
 * <p>
 * 租户表为平台级数据，不含 tenant_id/org_id，故继承 {@link IdBaseEntity}。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("tenant")
public class Tenant extends IdBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 租户编码（唯一） */
    private String tenantCode;

    /** 租户名称 */
    private String tenantName;

    /** 状态（{@link TenantStatusEnum}） */
    private TenantStatusEnum status;

    /** 联系人 */
    private String contactName;

    /** 联系电话 */
    private String contactPhone;

    /** 备注 */
    private String remark;
}
