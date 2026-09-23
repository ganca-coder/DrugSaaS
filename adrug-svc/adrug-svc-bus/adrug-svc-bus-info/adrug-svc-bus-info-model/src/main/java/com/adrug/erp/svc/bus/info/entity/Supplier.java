package com.adrug.erp.svc.bus.info.entity;

import com.adrug.erp.common.entity.BusBaseEntity;
import com.adrug.erp.svc.bus.info.enums.SupplierStatusEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 供应商信息实体（对应基础信息——供应商管理——供应商信息）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("supplier")
public class Supplier extends BusBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 供应商编码 */
    private String supplierCode;

    /** 供应商名称 */
    private String supplierName;

    /** 供应商状态（{@link SupplierStatusEnum}） */
    private SupplierStatusEnum status;

    /** 企业类型 */
    private String enterpriseType;

    /** 供应商分类 */
    private String supplierCategory;

    /** 税号 */
    private String taxNo;

    /** 结算方式 */
    private String settlementMethod;

    /** 法人代表 */
    private String legalRepresentative;

    /** 电话 */
    private String phone;

    /** 企业负责人 */
    private String enterpriseLeader;

    /** 质量负责人 */
    private String qualityLeader;

    /** 联系人 */
    private String contact;

    /** 联系人手机 */
    private String contactPhone;

    /** 联系人身份证 */
    private String contactIdCard;

    /** 公司地址 */
    private String companyAddress;

    /** 仓库地址 */
    private String warehouseAddress;

    /** 区域 */
    private String region;

    /** 省份 */
    private String province;

    /** 城市 */
    private String city;

    /** 区/县 */
    private String district;

    /** 传真 */
    private String fax;

    /** EMAIL */
    private String email;

    /** 邮政编码 */
    private String postcode;

    /** 预计送货天数 */
    private Integer expectedDeliveryDays;

    /** 进项税率(%) */
    private BigDecimal inputTaxRate;

    /** 发票类型 */
    private String invoiceType;

    /** 药监往来单位 */
    private String drugSupervisionUnit;

    /** 受托机构 */
    private String entrustedOrg;

    /** 助记码 */
    private String mnemonicCode;

    /** 拟供应品种 */
    private String plannedVariety;

    /** 上一年度报告 */
    private String lastYearReport;

    /** 印章样式 */
    private String sealStyle;

    /** 质量体系调查表 */
    private String qualitySystemSurvey;

    /** 随货同行单样式 */
    private String withGoodsBillStyle;

    /** 备注 */
    private String remark;
}
