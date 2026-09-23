package com.adrug.erp.svc.bus.info.vo;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.bus.info.enums.WarehouseStatusEnum;
import com.adrug.erp.svc.bus.info.enums.WarehouseTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 仓库信息视图对象（对外返回，不含租户、逻辑删除等内部字段）。
 * <p>
 * 枚举字段（仓库类型/状态）以 code 值表示，desc 字段通过 {@code getXxxDesc()} 提供。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class WarehouseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String warehouseCode;

    private String warehouseName;

    /** 仓库类型，对应枚举类 {@link WarehouseTypeEnum} */
    private Integer warehouseType;

    private String address;

    private String manager;

    private String phone;

    /** 状态，对应枚举类 {@link WarehouseStatusEnum} */
    private Integer status;

    private String remark;

    private Long orgId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 仓库类型描述
     */
    public String getWarehouseTypeDesc() {
        WarehouseTypeEnum typeEnum = BaseEnum.ofCode(WarehouseTypeEnum.class, warehouseType);
        return typeEnum == null ? null : typeEnum.getDesc();
    }

    /**
     * 状态描述
     */
    public String getStatusDesc() {
        WarehouseStatusEnum statusEnum = BaseEnum.ofCode(WarehouseStatusEnum.class, status);
        return statusEnum == null ? null : statusEnum.getDesc();
    }
}
