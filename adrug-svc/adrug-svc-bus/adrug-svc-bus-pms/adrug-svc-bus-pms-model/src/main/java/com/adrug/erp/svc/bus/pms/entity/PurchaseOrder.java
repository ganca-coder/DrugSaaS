package com.adrug.erp.svc.bus.pms.entity;

import com.adrug.erp.common.entity.BusBaseEntity;
import com.adrug.erp.svc.bus.pms.enums.BillStatusEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 采购订单主表实体（对应采购管理——采购订单）。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("purchase_order")
public class PurchaseOrder extends BusBaseEntity {

    private static final long serialVersionUID = 1L;

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

    /** 单据状态（{@link BillStatusEnum}） */
    private BillStatusEnum status;

    /** 采购数量合计 */
    private BigDecimal totalQty;

    /** 采购金额合计 */
    private BigDecimal totalAmount;

    /** 过账时间 */
    private LocalDateTime postTime;

    /** 取消理由 */
    private String cancelReason;

    /** 取消说明 */
    private String cancelNote;

    /** 备注 */
    private String remark;
}
