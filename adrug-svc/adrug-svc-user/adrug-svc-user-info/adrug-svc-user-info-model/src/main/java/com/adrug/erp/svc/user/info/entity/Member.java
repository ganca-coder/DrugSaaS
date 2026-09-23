package com.adrug.erp.svc.user.info.entity;

import com.adrug.erp.common.entity.BaseEntity;
import com.adrug.erp.svc.user.info.enums.GenderEnum;
import com.adrug.erp.svc.user.info.enums.MemberStatusEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 会员信息实体（对应会员管理——会员档案）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("member")
public class Member extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 会员卡号 */
    private String memberNo;

    /** 姓名 */
    private String name;

    /** 手机号 */
    private String phone;

    /** 性别（{@link GenderEnum} code） */
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

    /** 状态（{@link MemberStatusEnum} code） */
    private Integer status;

    /** 注册时间 */
    private LocalDateTime registerTime;

    /** 备注 */
    private String remark;
}
