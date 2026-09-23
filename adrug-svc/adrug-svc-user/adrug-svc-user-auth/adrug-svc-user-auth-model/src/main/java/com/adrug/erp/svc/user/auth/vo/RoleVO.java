package com.adrug.erp.svc.user.auth.vo;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.common.enums.DataScopeEnum;
import com.adrug.erp.svc.user.auth.enums.RoleStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色视图对象。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class RoleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String roleCode;

    private String roleName;

    /** 状态，对应枚举类 {@link RoleStatusEnum} */
    private Integer status;

    /** 数据范围，对应枚举类 {@link DataScopeEnum} */
    private Integer dataScope;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 状态描述
     */
    public String getStatusDesc() {
        RoleStatusEnum statusEnum = BaseEnum.ofCode(RoleStatusEnum.class, status);
        return statusEnum == null ? null : statusEnum.getDesc();
    }

    /**
     * 数据范围描述
     */
    public String getDataScopeDesc() {
        DataScopeEnum dataScopeEnum = BaseEnum.ofCode(DataScopeEnum.class, dataScope);
        return dataScopeEnum == null ? null : dataScopeEnum.getDesc();
    }
}
