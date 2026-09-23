package com.adrug.erp.svc.bus.pms.enums;

import com.adrug.erp.common.enums.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 验收单不合格处理方式枚举。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@AllArgsConstructor
public enum UnqualifiedHandleEnum implements BaseEnum<Integer> {

    /** 退货 */
    RETURN(1, "RETURN", "退货"),

    /** 换货 */
    EXCHANGE(2, "EXCHANGE", "换货"),

    /** 让步接收 */
    CONCESSION(3, "CONCESSION", "让步接收");

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
    public static UnqualifiedHandleEnum of(Integer code) {
        return BaseEnum.ofCode(UnqualifiedHandleEnum.class, code);
    }

    /**
     * 按 name 反查。
     */
    public static UnqualifiedHandleEnum ofName(String name) {
        return BaseEnum.ofName(UnqualifiedHandleEnum.class, name);
    }
}
