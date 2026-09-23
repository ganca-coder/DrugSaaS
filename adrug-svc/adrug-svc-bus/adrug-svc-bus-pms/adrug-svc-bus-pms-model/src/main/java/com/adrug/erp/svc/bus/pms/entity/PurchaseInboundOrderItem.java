package com.adrug.erp.svc.bus.pms.entity;

import com.adrug.erp.common.entity.BusBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 采购入库单明细表实体（对应采购管理——采购入库单——明细）。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("purchase_inbound_order_item")
public class PurchaseInboundOrderItem extends BusBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 入库单ID（关联 purchase_inbound_order.id） */
    private Long orderId;

    /** 序号 */
    private Integer seqNo;

    /** 商品ID（关联商品信息） */
    private Long drugId;

    /** 商品编码 */
    private String drugCode;

    /** 通用名称 */
    private String genericName;

    /** 商品名称 */
    private String drugName;

    /** 规格 */
    private String spec;

    /** 单位 */
    private String unit;

    /** 剂型 */
    private String dosageForm;

    /** 产地 */
    private String origin;

    /** 生产厂家 */
    private String manufacturer;

    /** 生产许可证凭证号 */
    private String productionLicenseNo;

    /** 上市持有人 */
    private String marketingHolder;

    /** 上市持有人地址 */
    private String marketingHolderAddress;

    /** 批准文号 */
    private String approvalNo;

    /** 批号 */
    private String batchNo;

    /** 生产日期 */
    private LocalDate productionDate;

    /** 有效期 */
    private LocalDate expiryDate;

    /** 入库数量 */
    private BigDecimal inboundQty;

    /** 单价 */
    private BigDecimal price;

    /** 金额 */
    private BigDecimal amount;

    /** 零售价 */
    private BigDecimal retailPrice;

    /** 会员价 */
    private BigDecimal memberPrice;

    /** 备注 */
    private String remark;
}
