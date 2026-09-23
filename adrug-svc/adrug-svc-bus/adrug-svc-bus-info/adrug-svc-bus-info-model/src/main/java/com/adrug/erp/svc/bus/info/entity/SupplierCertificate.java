package com.adrug.erp.svc.bus.info.entity;

import com.adrug.erp.common.entity.BusBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 供应商证件信息实体（对应基础信息——供应商管理——证件信息，从表）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("supplier_certificate")
public class SupplierCertificate extends BusBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 供应商ID（关联 supplier.id） */
    private Long supplierId;

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
