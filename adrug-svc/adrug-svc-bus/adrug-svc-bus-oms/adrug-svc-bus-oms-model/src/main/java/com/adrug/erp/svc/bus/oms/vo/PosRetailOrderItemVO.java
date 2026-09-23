package com.adrug.erp.svc.bus.oms.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * POS零售单明细视图对象。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class PosRetailOrderItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long orderId;

    private Integer seqNo;

    private Long drugId;

    private String drugCode;

    private String genericName;

    private String drugName;

    private String unit;

    private String spec;

    private String origin;

    private String batchNo;

    private LocalDate productionDate;

    private LocalDate expiryDate;

    private BigDecimal qty;

    private BigDecimal price;

    private BigDecimal amount;

    private BigDecimal discountRate;

    private BigDecimal discountAmount;

    private BigDecimal discountedPrice;

    private BigDecimal receivableAmount;

    private String remark;
}
