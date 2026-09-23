package com.adrug.erp.svc.bus.pms.query;

import com.adrug.erp.common.dto.PageQuery;
import com.adrug.erp.svc.bus.pms.enums.BillStatusEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 拒收单查询条件。
 * <p>
 * 覆盖 {@code RejectionOrder} 的全部业务与审计字段。枚举字段（单据状态）以 code 值表示。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class RejectionOrderQuery extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 关键字：拒收单号 / 往来单位名称 模糊匹配 */
    private String keyword;

    /** 主键 */
    private Long id;

    /** 租户 ID */
    private Long tenantId;

    /** 机构 ID（药店/门店） */
    private Long orgId;

    /** 拒收单号（精确匹配） */
    private String orderNo;

    /** 往来单位ID */
    private Long supplierId;

    /** 往来单位名称（精确匹配） */
    private String supplierName;

    /** 仓库ID */
    private Long warehouseId;

    /** 来货单号（精确匹配） */
    private String arrivalNo;

    /** 单据状态，对应枚举类 {@link BillStatusEnum} */
    private Integer status;

    /** 单据日期范围-起 */
    private LocalDate rejectDateStart;

    /** 单据日期范围-止 */
    private LocalDate rejectDateEnd;

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
