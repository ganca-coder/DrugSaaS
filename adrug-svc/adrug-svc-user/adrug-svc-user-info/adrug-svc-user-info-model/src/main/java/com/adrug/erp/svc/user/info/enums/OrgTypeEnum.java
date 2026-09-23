package com.adrug.erp.svc.user.info.enums;

import com.adrug.erp.common.enums.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 机构类型枚举
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@AllArgsConstructor
public enum OrgTypeEnum implements BaseEnum<Integer> {

    /** 总部 */
    HEADQUARTERS(1, "HEADQUARTERS", "总部"),

    /** 药店 */
    PHARMACY(2, "PHARMACY", "药店"),

    /** 门店 */
    STORE(3, "STORE", "门店");

    @EnumValue
    private final Integer code;

    private final String name;

    private final String desc;

    @JsonValue
    public Integer getCode() {
        return code;
    }

    @JsonCreator
    public static OrgTypeEnum of(Integer code) {
        return BaseEnum.ofCode(OrgTypeEnum.class, code);
    }

    public static OrgTypeEnum ofName(String name) {
        return BaseEnum.ofName(OrgTypeEnum.class, name);
    }
}
