package com.adrug.erp.svc.bus.info.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 供应商证件信息入参。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class SupplierCertificateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Integer seqNo;

    /** 证件类型 */
    private String certType;

    /** 证件号码 */
    private String certNo;

    /** 证件开始日期 */
    private LocalDate certStartDate;

    /** 证件有效期 */
    private LocalDate certExpiryDate;

    /** 经营范围 */
    private String businessScope;

    /** 发证机关 */
    private String issuingAuthority;

    /** 备注 */
    private String remark;
}
