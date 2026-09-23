package com.adrug.erp.svc.user.info.query;

import com.adrug.erp.common.dto.PageQuery;
import com.adrug.erp.svc.user.info.enums.GenderEnum;
import com.adrug.erp.svc.user.info.enums.MemberStatusEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 会员信息查询条件。
 * <p>
 * 覆盖 {@code Member} 的全部业务与审计字段，并针对日期/时间字段提供范围查询。
 * 枚举字段（性别/状态）以 code 值表示。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class MemberQuery extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 关键字：姓名 / 手机号 / 会员卡号 模糊匹配 */
    private String keyword;

    /** 主键 */
    private Long id;

    /** 租户 ID */
    private Long tenantId;

    /** 机构 ID（药店/门店） */
    private Long orgId;

    /** 会员卡号（精确匹配） */
    private String memberNo;

    /** 姓名（精确匹配） */
    private String name;

    /** 手机号（精确匹配） */
    private String phone;

    /** 性别，对应枚举类 {@link GenderEnum} */
    private Integer gender;

    /** 身份证号（精确匹配） */
    private String idCardNo;

    /** 会员等级（精确匹配） */
    private String level;

    /** 状态，对应枚举类 {@link MemberStatusEnum} */
    private Integer status;

    /** 出生日期范围-起 */
    private LocalDate birthDateStart;

    /** 出生日期范围-止 */
    private LocalDate birthDateEnd;

    /** 注册时间范围-起 */
    private LocalDateTime registerTimeStart;

    /** 注册时间范围-止 */
    private LocalDateTime registerTimeEnd;

    /** 创建人（用户 ID） */
    private Long createUserId;

    /** 更新人（用户 ID） */
    private Long updateUserId;

    /** 创建时间范围-起 */
    private LocalDateTime createTimeStart;

    /** 创建时间范围-止 */
    private LocalDateTime createTimeEnd;

    /** 更新时间范围-起 */
    private LocalDateTime updateTimeStart;

    /** 更新时间范围-止 */
    private LocalDateTime updateTimeEnd;
}
