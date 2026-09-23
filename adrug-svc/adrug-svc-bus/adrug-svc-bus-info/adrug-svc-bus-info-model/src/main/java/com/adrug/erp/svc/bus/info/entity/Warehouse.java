package com.adrug.erp.svc.bus.info.entity;

import com.adrug.erp.common.entity.BusBaseEntity;
import com.adrug.erp.svc.bus.info.enums.WarehouseStatusEnum;
import com.adrug.erp.svc.bus.info.enums.WarehouseTypeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 仓库信息实体（对应基础信息——仓库管理——仓库信息）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("warehouse")
public class Warehouse extends BusBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 仓库编码 */
    private String warehouseCode;

    /** 仓库名称 */
    private String warehouseName;

    /** 仓库类型（{@link WarehouseTypeEnum}） */
    private WarehouseTypeEnum warehouseType;

    /** 仓库地址 */
    private String address;

    /** 负责人 */
    private String manager;

    /** 联系电话 */
    private String phone;

    /** 状态（{@link WarehouseStatusEnum}） */
    private WarehouseStatusEnum status;

    /** 备注 */
    private String remark;
}
