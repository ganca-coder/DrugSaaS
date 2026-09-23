package com.adrug.erp.svc.bus.pms.entity;

import com.adrug.erp.common.entity.BusBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 采购订单明细表实体（对应采购管理——采购订单——明细）。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("purchase_order_item")
public class PurchaseOrderItem extends BusBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 采购订单ID（关联 purchase_order.id） */
    private Long orderId;

    /** 序号 */
    private Integer seqNo;

    /** 商品ID（关联商品信息） */
    private Long drugId;

    /** 药品编码 */
    private String drugCode;

    /** 通用药品名称 */
    private String genericName;

    /** 药品名称 */
    private String drugName;

    /** 规格 */
    private String spec;

    /** 单位 */
    private String unit;

    /** 产地 */
    private String origin;

    /** 生产厂家 */
    private String manufacturer;

    /** 采购数量 */
    private BigDecimal qty;

    /** 采购单价 */
    private BigDecimal price;

    /** 采购金额 */
    private BigDecimal amount;

    /** 收货数量 */
    private BigDecimal receivedQty;

    /** 收货金额 */
    private BigDecimal receivedAmount;

    /** 收货拒收数量 */
    private BigDecimal receivedRejectQty;

    /** 收货拒收金额 */
    private BigDecimal receivedRejectAmount;

    /** 源单据类型 */
    private String sourceType;

    /** 源单号 */
    private String sourceOrderNo;

    /** 备注 */
    private String remark;
}
