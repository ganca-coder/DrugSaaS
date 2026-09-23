package com.adrug.erp.svc.bus.pms.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 采购订单明细视图对象。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class PurchaseOrderItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long orderId;

    private Integer seqNo;

    private Long drugId;

    private String drugCode;

    private String genericName;

    private String drugName;

    private String spec;

    private String unit;

    private String origin;

    private String manufacturer;

    private BigDecimal qty;

    private BigDecimal price;

    private BigDecimal amount;

    private BigDecimal receivedQty;

    private BigDecimal receivedAmount;

    private BigDecimal receivedRejectQty;

    private BigDecimal receivedRejectAmount;

    private String sourceType;

    private String sourceOrderNo;

    private String remark;
}
