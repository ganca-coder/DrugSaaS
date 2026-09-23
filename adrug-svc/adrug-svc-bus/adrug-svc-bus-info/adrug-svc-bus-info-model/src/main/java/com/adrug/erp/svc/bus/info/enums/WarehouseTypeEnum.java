package com.adrug.erp.svc.bus.info.enums;

import com.adrug.erp.common.enums.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 仓库类型枚举。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@AllArgsConstructor
public enum WarehouseTypeEnum implements BaseEnum<Integer> {

    /** 合格仓 */
    QUALIFIED(1, "QUALIFIED", "合格仓"),

    /** 不合格仓 */
    UNQUALIFIED(2, "UNQUALIFIED", "不合格仓"),

    /** 待验区 */
    PENDING(3, "PENDING", "待验区"),

    /** 退货区 */
    RETURN(4, "RETURN", "退货区");

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
    public static WarehouseTypeEnum of(Integer code) {
        return BaseEnum.ofCode(WarehouseTypeEnum.class, code);
    }

    /**
     * 按 name 反查。
     */
    public static WarehouseTypeEnum ofName(String name) {
        return BaseEnum.ofName(WarehouseTypeEnum.class, name);
    }
}
