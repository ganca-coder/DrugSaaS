package com.adrug.erp.svc.user.info.enums;

import com.adrug.erp.common.enums.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别枚举
 *
 * @author 甘成安
 * @date 2026/9/16
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@AllArgsConstructor
public enum GenderEnum implements BaseEnum<Integer> {

    /** 未知 */
    UNKNOWN(0, "UNKNOWN", "未知"),

    /** 男 */
    MALE(1, "MALE", "男"),

    /** 女 */
    FEMALE(2, "FEMALE", "女");

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
    public static GenderEnum of(Integer code) {
        return BaseEnum.ofCode(GenderEnum.class, code);
    }

    /**
     * 按 name 反查。
     */
    public static GenderEnum ofName(String name) {
        return BaseEnum.ofName(GenderEnum.class, name);
    }
}
