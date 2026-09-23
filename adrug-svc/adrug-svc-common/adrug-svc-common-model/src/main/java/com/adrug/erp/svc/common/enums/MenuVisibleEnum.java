package com.adrug.erp.svc.common.enums;

import com.adrug.erp.common.enums.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单显示状态枚举
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@AllArgsConstructor
public enum MenuVisibleEnum implements BaseEnum<Integer> {

    /** 隐藏 */
    HIDDEN(0, "HIDDEN", "隐藏"),

    /** 显示 */
    SHOW(1, "SHOW", "显示");

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
    public static MenuVisibleEnum of(Integer code) {
        return BaseEnum.ofCode(MenuVisibleEnum.class, code);
    }

    /**
     * 按 name 反查。
     */
    public static MenuVisibleEnum ofName(String name) {
        return BaseEnum.ofName(MenuVisibleEnum.class, name);
    }
}
