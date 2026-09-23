package com.adrug.erp.svc.user.info.query;

import com.adrug.erp.common.dto.PageQuery;
import com.adrug.erp.svc.user.info.enums.EmployeeStatusEnum;
import com.adrug.erp.svc.user.info.enums.GenderEnum;
import com.adrug.erp.svc.user.info.enums.PharmacistFlagEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 员工信息查询条件。
 * <p>
 * 覆盖 {@code Employee} 的全部业务与审计字段，并针对日期/时间字段提供范围查询。
 * 枚举字段（性别/在职状态/是否药师）以 code 值表示。
 *
 * @author 甘成安
 * @date 2026/9/16
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class EmployeeQuery extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 关键字：姓名 / 工号 模糊匹配 */
    private String keyword;

    /** 主键 */
    private Long id;

    /** 租户 ID */
    private Long tenantId;

    /** 机构 ID（药店/门店） */
    private Long orgId;

    /** 工号（精确匹配） */
    private String empNo;

    /** 姓名（精确匹配） */
    private String name;

    /** 性别，对应枚举类 {@link GenderEnum} */
    private Integer gender;

    /** 出生日期（精确匹配） */
    private LocalDate birthDate;

    /** 出生日期范围-起 */
    private LocalDate birthDateStart;

    /** 出生日期范围-止 */
    private LocalDate birthDateEnd;

    /** 身份证号（精确匹配） */
    private String idCardNo;

    /** 手机号（精确匹配） */
    private String phone;

    /** 邮箱（精确匹配） */
    private String email;

    /** 岗位 ID */
    private Long positionId;

    /** 入职日期（精确匹配） */
    private LocalDate entryDate;

    /** 入职日期范围-起 */
    private LocalDate entryDateStart;

    /** 入职日期范围-止 */
    private LocalDate entryDateEnd;

    /** 离职日期（精确匹配） */
    private LocalDate resignDate;

    /** 离职日期范围-起 */
    private LocalDate resignDateStart;

    /** 离职日期范围-止 */
    private LocalDate resignDateEnd;

    /** 在职状态，对应枚举类 {@link EmployeeStatusEnum} */
    private Integer status;

    /** 是否药师，对应枚举类 {@link PharmacistFlagEnum} */
    private Integer pharmacist;

    /** 备注（精确匹配） */
    private String remark;

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
