package com.adrug.erp.svc.bus.info.enums;

import com.adrug.erp.common.enums.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 供应商状态枚举
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@AllArgsConstructor
public enum SupplierStatusEnum implements BaseEnum<Integer> {

    /** 停用 */
    DISABLED(0, "DISABLED", "停用"),

    /** 启用 */
    ENABLED(1, "ENABLED", "启用");

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
    public static SupplierStatusEnum of(Integer code) {
        return BaseEnum.ofCode(SupplierStatusEnum.class, code);
    }

    /**
     * 按 name 反查。
     */
    public static SupplierStatusEnum ofName(String name) {
        return BaseEnum.ofName(SupplierStatusEnum.class, name);
    }
}
