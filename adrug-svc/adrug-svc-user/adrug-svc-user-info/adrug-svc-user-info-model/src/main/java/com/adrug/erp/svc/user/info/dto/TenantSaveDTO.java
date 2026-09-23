package com.adrug.erp.svc.user.info.dto;

import com.adrug.erp.svc.user.info.enums.TenantStatusEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 租户新增/变更入参。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class TenantSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键（变更时必填） */
    private Long id;

    /** 租户编码（唯一） */
    private String tenantCode;

    /** 租户名称 */
    private String tenantName;

    /** 状态，对应枚举类 {@link TenantStatusEnum} */
    private Integer status;

    /** 联系人 */
    private String contactName;

    /** 联系电话 */
    private String contactPhone;

    /** 备注 */
    private String remark;
}
