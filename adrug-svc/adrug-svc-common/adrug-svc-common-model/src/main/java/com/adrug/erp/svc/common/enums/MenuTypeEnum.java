package com.adrug.erp.svc.common.enums;

import com.adrug.erp.common.enums.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型枚举
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum implements BaseEnum<Integer> {

    /** 目录 */
    DIR(1, "DIR", "目录"),

    /** 菜单 */
    MENU(2, "MENU", "菜单"),

    /** 按钮 */
    BUTTON(3, "BUTTON", "按钮");

    /** 类型码 */
    @EnumValue
    private final Integer code;

    /** 预留名称字段 */
    private final String name;

    /** 类型描述 */
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
    public static MenuTypeEnum of(Integer code) {
        return BaseEnum.ofCode(MenuTypeEnum.class, code);
    }

    /**
     * 按 name 反查。
     */
    public static MenuTypeEnum ofName(String name) {
        return BaseEnum.ofName(MenuTypeEnum.class, name);
    }
}
