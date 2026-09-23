package com.adrug.erp.svc.user.info.query;

import com.adrug.erp.common.dto.PageQuery;
import com.adrug.erp.svc.user.info.enums.AccountStatusEnum;
import com.adrug.erp.svc.user.info.enums.AccountTypeEnum;
import lombok.Data;

/**
 * 账号查询条件。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class AccountQuery extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 关键字：登录账号 模糊匹配 */
    private String keyword;

    /** 主键 */
    private Long id;

    /** 租户 ID（服务端从请求头上下文注入） */
    private Long tenantId;

    /** 机构 ID */
    private Long orgId;

    /** 登录账号（精确匹配） */
    private String username;

    /** 账号类型，对应枚举类 {@link AccountTypeEnum} */
    private Integer accountType;

    /** 关联员工 ID */
    private Long employeeId;

    /** 状态，对应枚举类 {@link AccountStatusEnum} */
    private Integer status;
}
