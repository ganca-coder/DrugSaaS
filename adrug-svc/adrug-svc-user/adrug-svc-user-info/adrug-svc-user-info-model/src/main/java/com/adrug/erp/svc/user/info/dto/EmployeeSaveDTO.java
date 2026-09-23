package com.adrug.erp.svc.user.info.dto;

import com.adrug.erp.svc.user.info.enums.EmployeeStatusEnum;
import com.adrug.erp.svc.user.info.enums.GenderEnum;
import com.adrug.erp.svc.user.info.enums.PharmacistFlagEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 员工信息新增/变更入参。
 * <p>
 * 与实体分离：审计字段、租户/机构字段由后端自动填充，不对外暴露。
 * 枚举字段（性别/在职状态/是否药师）以 code 值表示，业务层再转换为枚举。
 *
 * @author 甘成安
 * @date 2026/9/16
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class EmployeeSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键（变更时必填） */
    private Long id;

    /** 工号 */
    private String empNo;

    /** 姓名 */
    private String name;

    /** 性别，对应枚举类 {@link GenderEnum} */
    private Integer gender;

    /** 出生日期 */
    private LocalDate birthDate;

    /** 身份证号 */
    private String idCardNo;

    /** 手机号 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 岗位 ID（关联岗位信息） */
    private Long positionId;

    /** 入职日期 */
    private LocalDate entryDate;

    /** 离职日期 */
    private LocalDate resignDate;

    /** 在职状态，对应枚举类 {@link EmployeeStatusEnum} */
    private Integer status;

    /** 是否药师，对应枚举类 {@link PharmacistFlagEnum} */
    private Integer pharmacist;

    /** 备注 */
    private String remark;
}
