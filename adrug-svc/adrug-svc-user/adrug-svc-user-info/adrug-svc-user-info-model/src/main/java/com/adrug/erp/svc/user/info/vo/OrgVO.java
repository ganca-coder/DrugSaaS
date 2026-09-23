package com.adrug.erp.svc.user.info.vo;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.user.info.enums.OrgStatusEnum;
import com.adrug.erp.svc.user.info.enums.OrgTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 机构视图对象（不含租户、逻辑删除等内部字段）。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class OrgVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long parentId;

    private String orgCode;

    private String orgName;

    /** 机构类型，对应枚举类 {@link OrgTypeEnum} */
    private Integer orgType;

    /** 状态，对应枚举类 {@link OrgStatusEnum} */
    private Integer status;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 机构类型描述
     */
    public String getOrgTypeDesc() {
        OrgTypeEnum typeEnum = BaseEnum.ofCode(OrgTypeEnum.class, orgType);
        return typeEnum == null ? null : typeEnum.getDesc();
    }

    /**
     * 状态描述
     */
    public String getStatusDesc() {
        OrgStatusEnum statusEnum = BaseEnum.ofCode(OrgStatusEnum.class, status);
        return statusEnum == null ? null : statusEnum.getDesc();
    }
}
