package com.adrug.erp.svc.bus.pms.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * 拒收单新增/变更入参。
 * <p>
 * 与实体分离：审计字段、租户/机构字段、单据状态、过账时间由后端处理，不对外暴露。
 * 明细通过 {@code items} 一并提交。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class RejectionOrderSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键（变更时必填） */
    private Long id;

    /** 拒收单号 */
    private String orderNo;

    /** 单据日期 */
    private LocalDate rejectDate;

    /** 仓库ID（关联仓库信息） */
    private Long warehouseId;

    /** 仓库名称 */
    private String warehouseName;

    /** 数据来源 */
    private String sourceType;

    /** 往来单位ID */
    private Long supplierId;

    /** 往来单位名称 */
    private String supplierName;

    /** 来货单号 */
    private String arrivalNo;

    /** 经手人 */
    private String handler;

    /** 收货员 */
    private String receiver;

    /** 备注 */
    private String remark;

    /** 拒收单明细 */
    private List<RejectionOrderItemDTO> items;
}
