package com.adrug.erp.svc.user.info.entity;

import com.adrug.erp.common.entity.BaseEntity;
import com.adrug.erp.svc.user.info.enums.EmployeeStatusEnum;
import com.adrug.erp.svc.user.info.enums.GenderEnum;
import com.adrug.erp.svc.user.info.enums.PharmacistFlagEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 员工信息实体（对应基础信息——员工管理——员工信息）。
 *
 * @author 甘成安
 * @date 2026/9/16
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("employee")
public class Employee extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 工号 */
    private String empNo;

    /** 姓名 */
    private String name;

    /** 性别（{@link GenderEnum}） */
    private GenderEnum gender;

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

    /** 在职状态（{@link EmployeeStatusEnum}） */
    private EmployeeStatusEnum status;

    /** 是否药师（{@link PharmacistFlagEnum}） */
    private PharmacistFlagEnum pharmacist;

    /** 备注 */
    private String remark;
}
