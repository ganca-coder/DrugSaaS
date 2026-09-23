package com.adrug.erp.svc.user.auth.enums;

import com.adrug.erp.common.enums.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色状态枚举
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@AllArgsConstructor
public enum RoleStatusEnum implements BaseEnum<Integer> {

    /** 停用 */
    DISABLED(0, "DISABLED", "停用"),

    /** 启用 */
    ENABLED(1, "ENABLED", "启用");

    @EnumValue
    private final Integer code;

    private final String name;

    private final String desc;

    @JsonValue
    public Integer getCode() {
        return code;
    }

    @JsonCreator
    public static RoleStatusEnum of(Integer code) {
        return BaseEnum.ofCode(RoleStatusEnum.class, code);
    }

    public static RoleStatusEnum ofName(String name) {
        return BaseEnum.ofName(RoleStatusEnum.class, name);
    }
}
