package com.adrug.erp.svc.user.info.dto;

import com.adrug.erp.svc.user.info.enums.OrgStatusEnum;
import com.adrug.erp.svc.user.info.enums.OrgTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 机构新增/变更入参。
 * <p>
 * 租户 ID 由后端从请求头上下文自动填充，不对外暴露。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class OrgSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键（变更时必填） */
    private Long id;

    /** 上级机构 ID（0-顶级） */
    private Long parentId;

    /** 机构编码 */
    private String orgCode;

    /** 机构名称 */
    private String orgName;

    /** 机构类型，对应枚举类 {@link OrgTypeEnum} */
    private Integer orgType;

    /** 状态，对应枚举类 {@link OrgStatusEnum} */
    private Integer status;

    /** 备注 */
    private String remark;
}
