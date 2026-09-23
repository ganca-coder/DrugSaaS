package com.adrug.erp.svc.user.info.enums;

import com.adrug.erp.common.enums.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账号类型枚举
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@AllArgsConstructor
public enum AccountTypeEnum implements BaseEnum<Integer> {

    /** 管理员 */
    ADMIN(1, "ADMIN", "管理员"),

    /** 员工 */
    EMPLOYEE(2, "EMPLOYEE", "员工");

    @EnumValue
    private final Integer code;

    private final String name;

    private final String desc;

    @JsonValue
    public Integer getCode() {
        return code;
    }

    @JsonCreator
    public static AccountTypeEnum of(Integer code) {
        return BaseEnum.ofCode(AccountTypeEnum.class, code);
    }

    public static AccountTypeEnum ofName(String name) {
        return BaseEnum.ofName(AccountTypeEnum.class, name);
    }
}
