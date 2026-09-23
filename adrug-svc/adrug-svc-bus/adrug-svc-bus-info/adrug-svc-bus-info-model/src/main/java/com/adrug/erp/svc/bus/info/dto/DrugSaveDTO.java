package com.adrug.erp.svc.bus.info.dto;

import com.adrug.erp.svc.bus.info.enums.DrugCategoryEnum;
import com.adrug.erp.svc.bus.info.enums.DrugStatusEnum;
import com.adrug.erp.svc.bus.info.enums.DrugTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 药品信息新增/变更入参。
 * <p>
 * 与实体分离：审计字段、租户/机构字段由后端自动填充，不对外暴露。
 * 枚举字段（药品类型/状态/分类）以 code 值表示，业务层再转换为枚举。
 *
 * @author 甘成安
 * @date 2026/9/18
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class DrugSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键（变更时必填） */
    private Long id;

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

    /** 商品分类，对应枚举类 {@link DrugCategoryEnum} */
    private Integer category;

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

    /** 药品类型，对应枚举类 {@link DrugTypeEnum} */
    private Integer drugType;

    /** 状态，对应枚举类 {@link DrugStatusEnum} */
    private Integer status;

    /** 备注 */
    private String remark;
}
