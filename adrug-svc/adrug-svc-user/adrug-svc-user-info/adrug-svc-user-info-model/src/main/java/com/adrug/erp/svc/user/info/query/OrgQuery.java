package com.adrug.erp.svc.user.info.query;

import com.adrug.erp.common.dto.PageQuery;
import com.adrug.erp.svc.user.info.enums.OrgStatusEnum;
import com.adrug.erp.svc.user.info.enums.OrgTypeEnum;
import lombok.Data;

/**
 * 机构查询条件。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class OrgQuery extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 关键字：机构名称 / 编码 模糊匹配 */
    private String keyword;

    /** 主键 */
    private Long id;

    /** 租户 ID（服务端从请求头上下文注入） */
    private Long tenantId;

    /** 上级机构 ID（0-顶级） */
    private Long parentId;

    /** 机构编码（精确匹配） */
    private String orgCode;

    /** 机构名称（精确匹配） */
    private String orgName;

    /** 机构类型，对应枚举类 {@link OrgTypeEnum} */
    private Integer orgType;

    /** 状态，对应枚举类 {@link OrgStatusEnum} */
    private Integer status;
}
