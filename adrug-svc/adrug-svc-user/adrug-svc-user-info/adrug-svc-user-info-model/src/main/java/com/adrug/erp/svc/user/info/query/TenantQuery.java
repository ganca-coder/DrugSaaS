package com.adrug.erp.svc.user.info.query;

import com.adrug.erp.common.dto.PageQuery;
import com.adrug.erp.svc.user.info.enums.TenantStatusEnum;
import lombok.Data;

/**
 * 租户查询条件（平台级，无 tenant_id 过滤）。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class TenantQuery extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 关键字：租户名称 / 编码 模糊匹配 */
    private String keyword;

    /** 主键 */
    private Long id;

    /** 租户编码（精确匹配） */
    private String tenantCode;

    /** 租户名称（精确匹配） */
    private String tenantName;

    /** 状态，对应枚举类 {@link TenantStatusEnum} */
    private Integer status;
}
