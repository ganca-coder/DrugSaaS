package com.adrug.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据范围枚举（数据权限）。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@AllArgsConstructor
public enum DataScopeEnum implements BaseEnum<Integer> {

    /** 全部数据 */
    ALL(1, "ALL", "全部数据"),

    /** 自定义 */
    CUSTOM(2, "CUSTOM", "多机构及下级"),

    /** 本机构及下级 */
    ORG_AND_CHILD(3, "ORG_AND_CHILD", "本机构及下级"),

    /** 本机构 */
    ORG(4, "ORG", "本机构"),

    /** 仅本人 */
    SELF(5, "SELF", "仅本人");

    @EnumValue
    private final Integer code;

    private final String name;

    private final String desc;

    @JsonValue
    public Integer getCode() {
        return code;
    }

    @JsonCreator
    public static DataScopeEnum of(Integer code) {
        return BaseEnum.ofCode(DataScopeEnum.class, code);
    }

    public static DataScopeEnum ofName(String name) {
        return BaseEnum.ofName(DataScopeEnum.class, name);
    }
}
