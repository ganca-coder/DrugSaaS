package com.adrug.erp.svc.bus.pms.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 拒收单明细入参。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class RejectionOrderItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Integer seqNo;

    /** 商品ID（关联商品信息） */
    private Long drugId;

    /** 商品编码 */
    private String drugCode;

    /** 商品名称 */
    private String drugName;

    /** 单位 */
    private String unit;

    /** 规格 */
    private String spec;

    /** 剂型 */
    private String dosageForm;

    /** 产地 */
    private String origin;

    /** 批号 */
    private String batchNo;

    /** 生产日期 */
    private LocalDate productionDate;

    /** 有效期 */
    private LocalDate expiryDate;

    /** 拒收数量 */
    private BigDecimal rejectQty;

    /** 拒收原因 */
    private String rejectReason;

    /** 备注 */
    private String remark;
}
