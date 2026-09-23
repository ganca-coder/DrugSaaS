package com.adrug.erp.svc.bus.info.vo;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.bus.info.enums.SupplierStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 供应商信息视图对象（对外返回，不含租户、逻辑删除等内部字段）。
 * <p>
 * 枚举字段（供应商状态）以 code 值表示，desc 字段通过 {@code getStatusDesc()} 提供。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class SupplierVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String supplierCode;

    private String supplierName;

    /** 供应商状态，对应枚举类 {@link SupplierStatusEnum} */
    private Integer status;

    private String enterpriseType;

    private String supplierCategory;

    private String taxNo;

    private String settlementMethod;

    private String legalRepresentative;

    private String phone;

    private String enterpriseLeader;

    private String qualityLeader;

    private String contact;

    private String contactPhone;

    private String contactIdCard;

    private String companyAddress;

    private String warehouseAddress;

    private String region;

    private String province;

    private String city;

    private String district;

    private String fax;

    private String email;

    private String postcode;

    private Integer expectedDeliveryDays;

    private BigDecimal inputTaxRate;

    private String invoiceType;

    private String drugSupervisionUnit;

    private String entrustedOrg;

    private String mnemonicCode;

    private String plannedVariety;

    private String lastYearReport;

    private String sealStyle;

    private String qualitySystemSurvey;

    private String withGoodsBillStyle;

    private String remark;

    private Long orgId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /** 证件信息 */
    private List<SupplierCertificateVO> certificates;

    /**
     * 供应商状态描述
     */
    public String getStatusDesc() {
        SupplierStatusEnum statusEnum = BaseEnum.ofCode(SupplierStatusEnum.class, status);
        return statusEnum == null ? null : statusEnum.getDesc();
    }
}
