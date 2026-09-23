package com.adrug.erp.svc.bus.wms.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 库存视图对象（对外返回，不含租户、逻辑删除等内部字段）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class InventoryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long drugId;

    private String drugCode;

    private String drugName;

    private String spec;

    private String unit;

    private Long warehouseId;

    private String warehouseName;

    private String batchNo;

    private LocalDate productionDate;

    private LocalDate expiryDate;

    private BigDecimal quantity;

    private Long orgId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
