package com.adrug.erp.svc.bus.pms.entity;

import com.adrug.erp.common.entity.BusBaseEntity;
import com.adrug.erp.svc.bus.pms.enums.BillStatusEnum;
import com.adrug.erp.svc.bus.pms.enums.SourceTypeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 收货单主表实体（对应采购管理——收货单）。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("receiving_order")
public class ReceivingOrder extends BusBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 收货单号 */
    private String orderNo;

    /** 数据来源（{@link SourceTypeEnum}） */
    private SourceTypeEnum sourceType;

    /** 来源单据ID（采购订单ID等） */
    private Long sourceOrderId;

    /** 往来单位ID（供应商/退货单位） */
    private Long supplierId;

    /** 往来单位名称 */
    private String supplierName;

    /** 收货仓库ID（关联仓库信息） */
    private Long warehouseId;

    /** 收货仓库名称 */
    private String warehouseName;

    /** 来货单号 */
    private String arrivalNo;

    /** 收货日期 */
    private LocalDate receiveDate;

    /** 经手人 */
    private String handler;

    /** 收货员 */
    private String receiver;

    /** 开始收货时间 */
    private LocalDateTime receiveStartTime;

    /** 单据状态（{@link BillStatusEnum}） */
    private BillStatusEnum status;

    /** 收货数量合计 */
    private BigDecimal totalQty;

    /** 过账时间 */
    private LocalDateTime postTime;

    /** 备注 */
    private String remark;
}
