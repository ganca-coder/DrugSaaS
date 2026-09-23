package com.adrug.erp.svc.bus.info.entity;

import com.adrug.erp.common.entity.BusBaseEntity;
import com.adrug.erp.svc.bus.info.enums.DrugCategoryEnum;
import com.adrug.erp.svc.bus.info.enums.DrugStatusEnum;
import com.adrug.erp.svc.bus.info.enums.DrugTypeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 药品信息实体（对应基础信息——商品管理——商品信息）。
 *
 * @author 甘成安
 * @date 2026/9/18
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("drug")
public class Drug extends BusBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 药品编码 */
    private String drugCode;

    /** 药品名称 */
    private String drugName;

    /** 通用名 */
    private String genericName;

    /** 规格 */
    private String spec;

    /** 剂型 */
    private String dosageForm;

    /** 单位 */
    private String unit;

    /** 生产厂家 */
    private String manufacturer;

    /** 批准文号 */
    private String approvalNo;

    /** 主条形码 */
    private String mainBarcode;

    /** 商品分类（{@link DrugCategoryEnum}） */
    private DrugCategoryEnum category;

    /** 包装规格 */
    private String packageSpec;

    /** 产地 */
    private String origin;

    /** 上市持有人 */
    private String marketingHolder;

    /** 上市持有人地址 */
    private String marketingHolderAddress;

    /** 批文有效期 */
    private LocalDate approvalExpiryDate;

    /** 药监统一编码 */
    private String drugSupervisionCode;

    /** 条码一 */
    private String barcode1;

    /** 本位码 */
    private String standardCode;

    /** 储存条件 */
    private String storageCondition;

    /** 经营范围 */
    private String businessScope;

    /** 拆零规格 */
    private String splitSpec;

    /** 拆零单位 */
    private String splitUnit;

    /** 进价 */
    private BigDecimal purchasePrice;

    /** 零售价 */
    private BigDecimal retailPrice;

    /** 会员价 */
    private BigDecimal memberPrice;

    /** 拆零价 */
    private BigDecimal splitPrice;

    /** 建议零售价 */
    private BigDecimal suggestedRetailPrice;

    /** 国家医保编码 */
    private String nationalMedicalInsuranceCode;

    /** 助记码 */
    private String mnemonicCode;

    /** 药品类型（{@link DrugTypeEnum}） */
    private DrugTypeEnum drugType;

    /** 状态（{@link DrugStatusEnum}） */
    private DrugStatusEnum status;

    /** 备注 */
    private String remark;
}
