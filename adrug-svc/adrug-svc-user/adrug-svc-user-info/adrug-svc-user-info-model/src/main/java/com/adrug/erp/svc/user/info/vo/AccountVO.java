package com.adrug.erp.svc.user.info.vo;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.user.info.enums.AccountStatusEnum;
import com.adrug.erp.svc.user.info.enums.AccountTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 账号视图对象（不含密码、租户、逻辑删除等内部字段）。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class AccountVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    /** 账号类型，对应枚举类 {@link AccountTypeEnum} */
    private Integer accountType;

    private Long employeeId;

    /** 状态，对应枚举类 {@link AccountStatusEnum} */
    private Integer status;

    private String remark;

    private Long orgId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 账号类型描述
     */
    public String getAccountTypeDesc() {
        AccountTypeEnum typeEnum = BaseEnum.ofCode(AccountTypeEnum.class, accountType);
        return typeEnum == null ? null : typeEnum.getDesc();
    }

    /**
     * 状态描述
     */
    public String getStatusDesc() {
        AccountStatusEnum statusEnum = BaseEnum.ofCode(AccountStatusEnum.class, status);
        return statusEnum == null ? null : statusEnum.getDesc();
    }
}
