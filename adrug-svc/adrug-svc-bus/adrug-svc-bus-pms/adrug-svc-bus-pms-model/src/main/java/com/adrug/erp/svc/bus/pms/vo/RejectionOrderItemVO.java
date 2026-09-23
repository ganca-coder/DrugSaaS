package com.adrug.erp.svc.bus.pms.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 拒收单明细视图对象。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class RejectionOrderItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long orderId;

    private Integer seqNo;

    private Long drugId;

    private String drugCode;

    private String drugName;

    private String unit;

    private String spec;

    private String dosageForm;

    private String origin;

    private String batchNo;

    private LocalDate productionDate;

    private LocalDate expiryDate;

    private BigDecimal rejectQty;

    private String rejectReason;

    private String remark;
}
