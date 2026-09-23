package com.adrug.erp.svc.user.info.enums;

import com.adrug.erp.common.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会员状态枚举
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@AllArgsConstructor
public enum MemberStatusEnum implements BaseEnum<Integer> {

    /** 正常 */
    NORMAL(1, "NORMAL", "正常"),

    /** 停用 */
    DISABLED(0, "DISABLED", "停用");

    /** 状态码 */
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
    public static MemberStatusEnum of(Integer code) {
        return BaseEnum.ofCode(MemberStatusEnum.class, code);
    }

    /**
     * 按 name 反查。
     */
    public static MemberStatusEnum ofName(String name) {
        return BaseEnum.ofName(MemberStatusEnum.class, name);
    }
}
