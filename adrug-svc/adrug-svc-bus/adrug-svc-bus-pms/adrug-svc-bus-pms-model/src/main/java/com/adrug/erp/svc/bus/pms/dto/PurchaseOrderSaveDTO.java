package com.adrug.erp.svc.bus.pms.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 采购订单新增/变更入参。
 * <p>
 * 与实体分离：审计字段、租户/机构字段、单据状态、过账时间、金额合计由后端处理，不对外暴露。
 * 明细通过 {@code items} 一并提交。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class PurchaseOrderSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键（变更时必填） */
    private Long id;

    /** 采购订单号 */
    private String orderNo;

    /** 供应商ID（关联供应商信息） */
    private Long supplierId;

    /** 供应商名称 */
    private String supplierName;

    /** 供应商业务员 */
    private String supplierSalesman;

    /** 下单日期 */
    private LocalDate orderDate;

    /** 经手人 */
    private String handler;

    /** 采购员 */
    private String purchaser;

    /** 付款方式 */
    private String paymentMethod;

    /** 结算方式 */
    private String settlementMethod;

    /** 预结算日期 */
    private LocalDate preSettlementDate;

    /** 发票类型 */
    private String invoiceType;

    /** 送货方式 */
    private String deliveryMethod;

    /** 预计到货时间 */
    private LocalDateTime estimatedArrivalTime;

    /** 取消理由 */
    private String cancelReason;

    /** 取消说明 */
    private String cancelNote;

    /** 备注 */
    private String remark;

    /** 采购订单明细 */
    private List<PurchaseOrderItemDTO> items;
}
