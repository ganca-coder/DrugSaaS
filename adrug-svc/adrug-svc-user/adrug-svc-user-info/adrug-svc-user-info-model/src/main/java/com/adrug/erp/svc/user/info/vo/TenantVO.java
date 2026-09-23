package com.adrug.erp.svc.user.info.vo;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.user.info.enums.TenantStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 租户视图对象。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class TenantVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String tenantCode;

    private String tenantName;

    /** 状态，对应枚举类 {@link TenantStatusEnum} */
    private Integer status;

    private String contactName;

    private String contactPhone;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 状态描述
     */
    public String getStatusDesc() {
        TenantStatusEnum statusEnum = BaseEnum.ofCode(TenantStatusEnum.class, status);
        return statusEnum == null ? null : statusEnum.getDesc();
    }
}
