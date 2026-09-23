package com.adrug.erp.svc.bus.pms.enums;

import com.adrug.erp.common.enums.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 单据状态枚举（采购订单、收货单、验收单、采购入库单通用）。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@AllArgsConstructor
public enum BillStatusEnum implements BaseEnum<Integer> {

    /** 制单保存 */
    DRAFT(0, "DRAFT", "制单保存"),

    /** 制单完成 */
    COMPLETED(1, "COMPLETED", "制单完成");

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
    public static BillStatusEnum of(Integer code) {
        return BaseEnum.ofCode(BillStatusEnum.class, code);
    }

    /**
     * 按 name 反查。
     */
    public static BillStatusEnum ofName(String name) {
        return BaseEnum.ofName(BillStatusEnum.class, name);
    }
}
