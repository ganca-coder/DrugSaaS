package com.adrug.erp.svc.bus.info.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 供应商证件信息视图对象。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class SupplierCertificateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long supplierId;

    private Integer seqNo;

    private String certType;

    private String certNo;

    private LocalDate certStartDate;

    private LocalDate certExpiryDate;

    private String businessScope;

    private String issuingAuthority;

    private String remark;
}
