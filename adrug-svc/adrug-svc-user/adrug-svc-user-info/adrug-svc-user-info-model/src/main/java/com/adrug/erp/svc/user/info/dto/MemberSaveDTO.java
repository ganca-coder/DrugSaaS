package com.adrug.erp.svc.user.info.dto;

import com.adrug.erp.svc.user.info.enums.GenderEnum;
import com.adrug.erp.svc.user.info.enums.MemberStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 会员信息新增/变更入参。
 * <p>
 * 与实体分离：审计字段、租户/机构字段由后端自动填充，不对外暴露。
 * 枚举字段（性别/状态）以 code 值表示。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class MemberSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键（变更时必填） */
    private Long id;

    /** 会员卡号 */
    private String memberNo;

    /** 姓名 */
    private String name;

    /** 手机号 */
    private String phone;

    /** 性别，对应枚举类 {@link GenderEnum} */
    private Integer gender;

    /** 身份证号 */
    private String idCardNo;

    /** 出生日期 */
    private LocalDate birthDate;

    /** 会员等级 */
    private String level;

    /** 折扣率（0.95 表示 95折） */
    private BigDecimal discountRate;

    /** 积分 */
    private BigDecimal points;

    /** 储值余额 */
    private BigDecimal balance;

    /** 状态，对应枚举类 {@link MemberStatusEnum} */
    private Integer status;

    /** 注册时间 */
    private LocalDateTime registerTime;

    /** 备注 */
    private String remark;
}
