package com.adrug.erp.svc.bus.pms.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 采购订单明细入参。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class PurchaseOrderItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /** 采购金额（缺省由后端按 数量×单价 计算） */
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
