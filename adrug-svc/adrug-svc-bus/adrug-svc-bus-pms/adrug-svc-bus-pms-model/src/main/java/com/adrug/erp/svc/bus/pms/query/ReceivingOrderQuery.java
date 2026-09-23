package com.adrug.erp.svc.bus.pms.query;

import com.adrug.erp.common.dto.PageQuery;
import com.adrug.erp.svc.bus.pms.enums.BillStatusEnum;
import com.adrug.erp.svc.bus.pms.enums.SourceTypeEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 收货单查询条件。
 * <p>
 * 覆盖 {@code ReceivingOrder} 的全部业务与审计字段，并针对日期/时间字段提供范围查询。
 * 枚举字段（数据来源/单据状态）以 code 值表示。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class ReceivingOrderQuery extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 关键字：收货单号 / 往来单位名称 模糊匹配 */
    private String keyword;

    /** 主键 */
    private Long id;

    /** 租户 ID */
    private Long tenantId;

    /** 机构 ID（药店/门店） */
    private Long orgId;

    /** 收货单号（精确匹配） */
    private String orderNo;

    /** 数据来源，对应枚举类 {@link SourceTypeEnum} */
    private Integer sourceType;

    /** 来源单据ID */
    private Long sourceOrderId;

    /** 往来单位ID */
    private Long supplierId;

    /** 往来单位名称（精确匹配） */
    private String supplierName;

    /** 收货仓库ID */
    private Long warehouseId;

    /** 来货单号（精确匹配） */
    private String arrivalNo;

    /** 单据状态，对应枚举类 {@link BillStatusEnum} */
    private Integer status;

    /** 收货日期范围-起 */
    private LocalDate receiveDateStart;

    /** 收货日期范围-止 */
    private LocalDate receiveDateEnd;

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
