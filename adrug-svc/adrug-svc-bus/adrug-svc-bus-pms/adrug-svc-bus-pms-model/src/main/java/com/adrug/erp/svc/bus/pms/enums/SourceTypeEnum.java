package com.adrug.erp.svc.bus.pms.enums;

import com.adrug.erp.common.enums.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 收货单数据来源枚举。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@AllArgsConstructor
public enum SourceTypeEnum implements BaseEnum<Integer> {

    /** 采购订单 */
    PURCHASE_ORDER(1, "PURCHASE_ORDER", "采购订单"),

    /** 退货 */
    RETURN_ORDER(2, "RETURN_ORDER", "退货");

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
    public static SourceTypeEnum of(Integer code) {
        return BaseEnum.ofCode(SourceTypeEnum.class, code);
    }

    /**
     * 按 name 反查。
     */
    public static SourceTypeEnum ofName(String name) {
        return BaseEnum.ofName(SourceTypeEnum.class, name);
    }
}
