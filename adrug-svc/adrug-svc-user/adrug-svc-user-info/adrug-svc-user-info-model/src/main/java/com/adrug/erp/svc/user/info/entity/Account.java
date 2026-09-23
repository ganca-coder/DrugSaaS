package com.adrug.erp.svc.user.info.entity;

import com.adrug.erp.common.entity.BaseEntity;
import com.adrug.erp.svc.user.info.enums.AccountStatusEnum;
import com.adrug.erp.svc.user.info.enums.AccountTypeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 账号实体（对应通用库 account 表）。
 * <p>
 * 含租户/机构隔离字段，继承 {@link BaseEntity}。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("account")
public class Account extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 登录账号 */
    private String username;

    /** 密码（BCrypt 加密存储） */
    private String password;

    /** 账号类型（{@link AccountTypeEnum}） */
    private AccountTypeEnum accountType;

    /** 关联员工 ID */
    private Long employeeId;

    /** 状态（{@link AccountStatusEnum}） */
    private AccountStatusEnum status;

    /** 备注 */
    private String remark;
}
