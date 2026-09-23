package com.adrug.erp.svc.user.info.enums;

import com.adrug.erp.common.enums.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 员工在职状态枚举
 *
 * @author 甘成安
 * @date 2026/9/16
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@AllArgsConstructor
public enum EmployeeStatusEnum implements BaseEnum<Integer> {

    /** 在职 */
    EMPLOYED(1, "EMPLOYED", "在职"),

    /** 离职 */
    RESIGNED(2, "RESIGNED", "离职");

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
    public static EmployeeStatusEnum of(Integer code) {
        return BaseEnum.ofCode(EmployeeStatusEnum.class, code);
    }

    /**
     * 按 name 反查。
     */
    public static EmployeeStatusEnum ofName(String name) {
        return BaseEnum.ofName(EmployeeStatusEnum.class, name);
    }
}
