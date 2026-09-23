package com.adrug.erp.svc.bus.wms.entity;

import com.adrug.erp.common.entity.BusBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 库存实体（对应库存管理——库存查询）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("inventory")
public class Inventory extends BusBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 商品ID（关联商品信息） */
    private Long drugId;

    /** 药品编码 */
    private String drugCode;

    /** 药品名称 */
    private String drugName;

    /** 规格 */
    private String spec;

    /** 单位 */
    private String unit;

    /** 仓库ID（关联仓库信息） */
    private Long warehouseId;

    /** 仓库名称 */
    private String warehouseName;

    /** 批号 */
    private String batchNo;

    /** 生产日期 */
    private LocalDate productionDate;

    /** 有效期 */
    private LocalDate expiryDate;

    /** 库存数量 */
    private BigDecimal quantity;
}
