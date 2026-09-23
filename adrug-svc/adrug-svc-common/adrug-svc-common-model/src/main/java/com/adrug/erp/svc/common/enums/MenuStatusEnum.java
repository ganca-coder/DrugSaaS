package com.adrug.erp.svc.common.enums;

import com.adrug.erp.common.enums.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单启用状态枚举
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@AllArgsConstructor
public enum MenuStatusEnum implements BaseEnum<Integer> {

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
    public static MenuStatusEnum of(Integer code) {
        return BaseEnum.ofCode(MenuStatusEnum.class, code);
    }

    /**
     * 按 name 反查。
     */
    public static MenuStatusEnum ofName(String name) {
        return BaseEnum.ofName(MenuStatusEnum.class, name);
    }
}
