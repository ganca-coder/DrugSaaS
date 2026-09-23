package com.adrug.erp.svc.bus.oms.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * POS零售单明细入参。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class PosRetailOrderItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /** 单位 */
    private String unit;

    /** 规格 */
    private String spec;

    /** 产地 */
    private String origin;

    /** 批号 */
    private String batchNo;

    /** 生产日期 */
    private LocalDate productionDate;

    /** 有效期 */
    private LocalDate expiryDate;

    /** 数量 */
    private BigDecimal qty;

    /** 单价 */
    private BigDecimal price;

    /** 金额（缺省由后端按 数量×单价 计算） */
    private BigDecimal amount;

    /** 折扣率 */
    private BigDecimal discountRate;

    /** 优惠金额 */
    private BigDecimal discountAmount;

    /** 折后单价 */
    private BigDecimal discountedPrice;

    /** 应收金额（缺省由后端按 金额-优惠金额 计算） */
    private BigDecimal receivableAmount;

    /** 备注 */
    private String remark;
}
