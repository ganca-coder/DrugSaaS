package com.adrug.erp.svc.bus.pms.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * 采购入库单新增/变更入参。
 * <p>
 * 与实体分离：审计字段、租户/机构字段、单据状态、过账时间、数量/金额合计由后端处理，不对外暴露。
 * 明细通过 {@code items} 一并提交。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class PurchaseInboundOrderSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键（变更时必填） */
    private Long id;

    /** 入库单号 */
    private String orderNo;

    /** 供应商ID（关联供应商信息） */
    private Long supplierId;

    /** 供应商名称 */
    private String supplierName;

    /** 入库仓库ID（关联仓库信息） */
    private Long warehouseId;

    /** 入库仓库名称 */
    private String warehouseName;

    /** 发票类型 */
    private String invoiceType;

    /** 发票号码 */
    private String invoiceNo;

    /** 经手人 */
    private String handler;

    /** 供应商业务员 */
    private String supplierSalesman;

    /** 付款方式 */
    private String paymentMethod;

    /** 结算方式 */
    private String settlementMethod;

    /** 预结算日期 */
    private LocalDate preSettlementDate;

    /** 来货单号 */
    private String arrivalNo;

    /** 验收人 */
    private String acceptor;

    /** 来源验收单ID（关联 acceptance_order.id） */
    private Long sourceOrderId;

    /** 入库日期 */
    private LocalDate inboundDate;

    /** 备注 */
    private String remark;

    /** 采购入库单明细 */
    private List<PurchaseInboundOrderItemDTO> items;
}
