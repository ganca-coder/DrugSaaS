package com.adrug.erp.svc.bus.info.vo;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.bus.info.enums.DrugCategoryEnum;
import com.adrug.erp.svc.bus.info.enums.DrugStatusEnum;
import com.adrug.erp.svc.bus.info.enums.DrugTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 药品信息视图对象（对外返回，不含租户、逻辑删除等内部字段）。
 * <p>
 * 枚举字段（药品类型/状态/分类）以 code 值表示，desc 字段通过 {@code getXxxDesc()} 提供。
 *
 * @author 甘成安
 * @date 2026/9/18
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class DrugVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String drugCode;

    private String drugName;

    private String genericName;

    private String spec;

    private String dosageForm;

    private String unit;

    private String manufacturer;

    private String approvalNo;

    private String mainBarcode;

    /** 商品分类，对应枚举类 {@link DrugCategoryEnum} */
    private Integer category;

    private String packageSpec;

    private String origin;

    private String marketingHolder;

    private String marketingHolderAddress;

    private LocalDate approvalExpiryDate;

    private String drugSupervisionCode;

    private String barcode1;

    private String standardCode;

    private String storageCondition;

    private String businessScope;

    private String splitSpec;

    private String splitUnit;

    private BigDecimal purchasePrice;

    private BigDecimal retailPrice;

    private BigDecimal memberPrice;

    private BigDecimal splitPrice;

    private BigDecimal suggestedRetailPrice;

    private String nationalMedicalInsuranceCode;

    private String mnemonicCode;

    /** 药品类型，对应枚举类 {@link DrugTypeEnum} */
    private Integer drugType;

    /** 状态，对应枚举类 {@link DrugStatusEnum} */
    private Integer status;

    private String remark;

    private Long orgId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 商品分类描述
     */
    public String getCategoryDesc() {
        DrugCategoryEnum categoryEnum = BaseEnum.ofCode(DrugCategoryEnum.class, category);
        return categoryEnum == null ? null : categoryEnum.getDesc();
    }

    /**
     * 药品类型描述
     */
    public String getDrugTypeDesc() {
        DrugTypeEnum drugTypeEnum = BaseEnum.ofCode(DrugTypeEnum.class, drugType);
        return drugTypeEnum == null ? null : drugTypeEnum.getDesc();
    }

    /**
     * 状态描述
     */
    public String getStatusDesc() {
        DrugStatusEnum statusEnum = BaseEnum.ofCode(DrugStatusEnum.class, status);
        return statusEnum == null ? null : statusEnum.getDesc();
    }
}
