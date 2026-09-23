package com.adrug.erp.svc.bus.info.enums;

import com.adrug.erp.common.enums.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 药品类型枚举
 *
 * @author 甘成安
 * @date 2026/9/18
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@AllArgsConstructor
public enum DrugTypeEnum implements BaseEnum<Integer> {

    /** 处方药 */
    PRESCRIPTION(1, "PRESCRIPTION", "处方药"),

    /** 非处方药 */
    OTC(2, "OTC", "非处方药");

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
    public static DrugTypeEnum of(Integer code) {
        return BaseEnum.ofCode(DrugTypeEnum.class, code);
    }

    /**
     * 按 name 反查。
     */
    public static DrugTypeEnum ofName(String name) {
        return BaseEnum.ofName(DrugTypeEnum.class, name);
    }
}
