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
 * 采购入库单主表实体（对应采购管理——采购入库单）。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("purchase_inbound_order")
public class PurchaseInboundOrder extends BusBaseEntity {

    private static final long serialVersionUID = 1L;

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

    /** 单据状态（{@link BillStatusEnum}） */
    private BillStatusEnum status;

    /** 入库数量合计 */
    private BigDecimal totalQty;

    /** 入库金额合计 */
    private BigDecimal totalAmount;

    /** 过账时间 */
    private LocalDateTime postTime;

    /** 备注 */
    private String remark;
}
