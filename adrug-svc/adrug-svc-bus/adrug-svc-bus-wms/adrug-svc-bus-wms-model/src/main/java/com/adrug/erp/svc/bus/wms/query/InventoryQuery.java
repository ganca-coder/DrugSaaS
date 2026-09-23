package com.adrug.erp.svc.bus.wms.query;

import com.adrug.erp.common.dto.PageQuery;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 库存查询条件。
 * <p>
 * 覆盖 {@code Inventory} 的全部业务与审计字段，并针对日期/时间字段提供范围查询。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class InventoryQuery extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 关键字：药品名称 / 药品编码 模糊匹配 */
    private String keyword;

    /** 主键 */
    private Long id;

    /** 租户 ID */
    private Long tenantId;

    /** 机构 ID（药店/门店） */
    private Long orgId;

    /** 商品ID */
    private Long drugId;

    /** 药品编码（精确匹配） */
    private String drugCode;

    /** 仓库ID */
    private Long warehouseId;

    /** 批号（精确匹配） */
    private String batchNo;

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
