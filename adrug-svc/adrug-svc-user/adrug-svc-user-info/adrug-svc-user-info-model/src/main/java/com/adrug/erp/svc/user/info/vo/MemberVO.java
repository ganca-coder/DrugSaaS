package com.adrug.erp.svc.user.info.vo;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.user.info.enums.GenderEnum;
import com.adrug.erp.svc.user.info.enums.MemberStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 会员信息视图对象（对外返回，不含租户、逻辑删除等内部字段）。
 * <p>
 * 枚举字段（性别/状态）以 code 值表示，desc 字段通过 {@code getXxxDesc()} 提供。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class MemberVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String memberNo;

    private String name;

    private String phone;

    /** 性别，对应枚举类 {@link GenderEnum} */
    private Integer gender;

    private String idCardNo;

    private LocalDate birthDate;

    private String level;

    /** 折扣率（0.95 表示 95折） */
    private BigDecimal discountRate;

    private BigDecimal points;

    private BigDecimal balance;

    /** 状态，对应枚举类 {@link MemberStatusEnum} */
    private Integer status;

    private LocalDateTime registerTime;

    private String remark;

    private Long orgId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 性别描述
     */
    public String getGenderDesc() {
        GenderEnum genderEnum = BaseEnum.ofCode(GenderEnum.class, gender);
        return genderEnum == null ? null : genderEnum.getDesc();
    }

    /**
     * 状态描述
     */
    public String getStatusDesc() {
        MemberStatusEnum statusEnum = BaseEnum.ofCode(MemberStatusEnum.class, status);
        return statusEnum == null ? null : statusEnum.getDesc();
    }
}
