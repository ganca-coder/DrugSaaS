package com.adrug.erp.svc.bus.pms.entity;

import com.adrug.erp.common.entity.BusBaseEntity;
import com.adrug.erp.svc.bus.pms.enums.BillStatusEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 验收单主表实体（对应采购管理——验收单）。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("acceptance_order")
public class AcceptanceOrder extends BusBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 验收单号 */
    private String orderNo;

    /** 供应商ID（关联供应商信息） */
    private Long supplierId;

    /** 供应商名称 */
    private String supplierName;

    /** 验收仓库ID（关联仓库信息） */
    private Long warehouseId;

    /** 验收仓库名称 */
    private String warehouseName;

    /** 数据来源 */
    private String sourceType;

    /** 经手人 */
    private String handler;

    /** 来源收货单ID（关联 receiving_order.id） */
    private Long sourceOrderId;

    /** 验收日期 */
    private LocalDate acceptDate;

    /** 验收人ID（员工ID） */
    private Long acceptUserId;

    /** 验收时间 */
    private LocalDateTime acceptTime;

    /** 单据状态（{@link BillStatusEnum}） */
    private BillStatusEnum status;

    /** 验收数量合计 */
    private BigDecimal totalQty;

    /** 过账时间 */
    private LocalDateTime postTime;

    /** 备注 */
    private String remark;
}
