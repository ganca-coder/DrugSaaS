package com.adrug.erp.svc.user.info.vo;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.user.info.enums.EmployeeStatusEnum;
import com.adrug.erp.svc.user.info.enums.GenderEnum;
import com.adrug.erp.svc.user.info.enums.PharmacistFlagEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 员工信息视图对象（对外返回，不含租户、逻辑删除等内部字段）。
 * <p>
 * 枚举字段（性别/在职状态/是否药师）以 code 值表示，desc 字段通过 {@code getXxxDesc()} 提供。
 *
 * @author 甘成安
 * @date 2026/9/16
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class EmployeeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String empNo;

    private String name;

    /** 性别，对应枚举类 {@link GenderEnum} */
    private Integer gender;

    private LocalDate birthDate;

    private String idCardNo;

    private String phone;

    private String email;

    private Long positionId;

    private LocalDate entryDate;

    private LocalDate resignDate;

    /** 在职状态，对应枚举类 {@link EmployeeStatusEnum} */
    private Integer status;

    /** 是否药师，对应枚举类 {@link PharmacistFlagEnum} */
    private Integer pharmacist;

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
     * 在职状态描述
     */
    public String getStatusDesc() {
        EmployeeStatusEnum statusEnum = BaseEnum.ofCode(EmployeeStatusEnum.class, status);
        return statusEnum == null ? null : statusEnum.getDesc();
    }

    /**
     * 是否药师描述
     */
    public String getPharmacistDesc() {
        PharmacistFlagEnum pharmacistEnum = BaseEnum.ofCode(PharmacistFlagEnum.class, pharmacist);
        return pharmacistEnum == null ? null : pharmacistEnum.getDesc();
    }
}
