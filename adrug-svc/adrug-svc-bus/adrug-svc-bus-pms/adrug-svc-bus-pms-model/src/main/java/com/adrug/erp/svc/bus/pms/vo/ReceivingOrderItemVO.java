package com.adrug.erp.svc.bus.pms.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 收货单明细视图对象。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class ReceivingOrderItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long orderId;

    private Integer seqNo;

    private Long drugId;

    private String drugCode;

    private String mainBarcode;

    private String genericName;

    private String drugName;

    private String spec;

    private String unit;

    private String dosageForm;

    private String origin;

    private String manufacturer;

    private String marketingHolder;

    private String marketingHolderAddress;

    private String approvalNo;

    private String batchNo;

    private LocalDate productionDate;

    private LocalDate expiryDate;

    private String location;

    private BigDecimal notifyQty;

    private BigDecimal receiveQty;

    private BigDecimal price;

    private BigDecimal amount;

    private BigDecimal rejectQty;

    private BigDecimal rejectAmount;

    private String rejectReason;

    private String receiver;

    private LocalDateTime receiveTime;

    private String sourceType;

    private String sourceOrderNo;

    private String remark;
}
