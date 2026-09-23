package com.adrug.erp.svc.bus.info.enums;

import com.adrug.erp.common.enums.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 药品（商品）分类枚举。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@AllArgsConstructor
public enum DrugCategoryEnum implements BaseEnum<Integer> {

    /** 普通药品 */
    ORDINARY_DRUG(1, "ORDINARY_DRUG", "普通药品"),

    /** 中药饮片 */
    TCM_SLICES(2, "TCM_SLICES", "中药饮片"),

    /** 精制饮片 */
    REFINED_SLICES(3, "REFINED_SLICES", "精制饮片"),

    /** 医疗器械 */
    MEDICAL_DEVICE(4, "MEDICAL_DEVICE", "医疗器械"),

    /** 保健食品 */
    HEALTH_FOOD(5, "HEALTH_FOOD", "保健食品"),

    /** 赠品 */
    GIFT(6, "GIFT", "赠品"),

    /** 其他 */
    OTHER(7, "OTHER", "其他");

    /** 状态码 */
    @EnumValue
    private final Integer code;

    /** 预留名称字段 */
    private final String name;

    /** 状态描述 */
    private final String desc;

    /**
     * 序列化为 code。
     */
    @JsonValue
    public Integer getCode() {
        return code;
    }

    /**
     * 按 code 反序列化。
     */
    @JsonCreator
    public static DrugCategoryEnum of(Integer code) {
        return BaseEnum.ofCode(DrugCategoryEnum.class, code);
    }

    /**
     * 按 name 反查。
     */
    public static DrugCategoryEnum ofName(String name) {
        return BaseEnum.ofName(DrugCategoryEnum.class, name);
    }
}
