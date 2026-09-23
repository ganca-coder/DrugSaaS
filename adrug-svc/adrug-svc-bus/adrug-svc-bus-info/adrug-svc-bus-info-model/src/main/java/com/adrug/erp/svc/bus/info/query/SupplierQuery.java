package com.adrug.erp.svc.bus.info.query;

import com.adrug.erp.common.dto.PageQuery;
import com.adrug.erp.svc.bus.info.enums.SupplierStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 供应商信息查询条件。
 * <p>
 * 覆盖 {@code Supplier} 的全部业务与审计字段。枚举字段（供应商状态）以 code 值表示。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class SupplierQuery extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 关键字：供应商名称 / 供应商编码 模糊匹配 */
    private String keyword;

    /** 主键 */
    private Long id;

    /** 租户 ID */
    private Long tenantId;

    /** 机构 ID（药店/门店） */
    private Long orgId;

    /** 供应商编码（精确匹配） */
    private String supplierCode;

    /** 供应商名称（精确匹配） */
    private String supplierName;

    /** 供应商状态，对应枚举类 {@link SupplierStatusEnum} */
    private Integer status;

    /** 创建人（用户 ID） */
    private Long createUserId;

    /** 更新人（用户 ID） */
    private Long updateUserId;

    /** 创建时间范围-起 */
    private LocalDateTime createTimeStart;

    /** 创建时间范围-止 */
    private LocalDateTime createTimeEnd;

    /** 更新时间范围-起 */
    private LocalDateTime updateTimeStart;

    /** 更新时间范围-止 */
    private LocalDateTime updateTimeEnd;
}
