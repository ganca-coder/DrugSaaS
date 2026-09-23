package com.adrug.erp.svc.bus.pms.entity;

import com.adrug.erp.common.entity.BusBaseEntity;
import com.adrug.erp.svc.bus.pms.enums.BillStatusEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 拒收单主表实体（对应采购管理——拒收单）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("rejection_order")
public class RejectionOrder extends BusBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 拒收单号 */
    private String orderNo;

    /** 单据日期 */
    private LocalDate rejectDate;

    /** 仓库ID（关联仓库信息） */
    private Long warehouseId;

    /** 仓库名称 */
    private String warehouseName;

    /** 数据来源 */
    private String sourceType;

    /** 往来单位ID */
    private Long supplierId;

    /** 往来单位名称 */
    private String supplierName;

    /** 来货单号 */
    private String arrivalNo;

    /** 经手人 */
    private String handler;

    /** 收货员 */
    private String receiver;

    /** 单据状态（{@link BillStatusEnum}） */
    private BillStatusEnum status;

    /** 过账时间 */
    private LocalDateTime postTime;

    /** 备注 */
    private String remark;
}
