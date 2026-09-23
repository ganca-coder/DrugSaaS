package com.adrug.erp.svc.user.info.dto;

import com.adrug.erp.svc.user.info.enums.AccountStatusEnum;
import com.adrug.erp.svc.user.info.enums.AccountTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 账号新增/变更入参。
 * <p>
 * 密码为明文入参（新增时必填，变更时选填，不填则不修改），后端 BCrypt 加密后存储；
 * 租户/机构字段由后端从请求头上下文自动填充，不对外暴露。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class AccountSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键（变更时必填） */
    private Long id;

    /** 登录账号 */
    private String username;

    /** 密码（明文，后端加密存储） */
    private String password;

    /** 账号类型，对应枚举类 {@link AccountTypeEnum} */
    private Integer accountType;

    /** 关联员工 ID */
    private Long employeeId;

    /** 状态，对应枚举类 {@link AccountStatusEnum} */
    private Integer status;

    /** 备注 */
    private String remark;
}
