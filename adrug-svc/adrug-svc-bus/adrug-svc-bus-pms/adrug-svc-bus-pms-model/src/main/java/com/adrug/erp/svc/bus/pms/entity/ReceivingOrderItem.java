package com.adrug.erp.svc.bus.pms.entity;

import com.adrug.erp.common.entity.BusBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 收货单明细表实体（对应采购管理——收货单——明细）。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("receiving_order_item")
public class ReceivingOrderItem extends BusBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 收货单ID（关联 receiving_order.id） */
    private Long orderId;

    /** 序号 */
    private Integer seqNo;

    /** 商品ID（关联商品信息） */
    private Long drugId;

    /** 商品编码 */
    private String drugCode;

    /** 主条形码 */
    private String mainBarcode;

    /** 通用名称 */
    private String genericName;

    /** 商品名称 */
    private String drugName;

    /** 规格 */
    private String spec;

    /** 单位 */
    private String unit;

    /** 剂型 */
    private String dosageForm;

    /** 产地 */
    private String origin;

    /** 生产厂家 */
    private String manufacturer;

    /** 上市许可持有人 */
    private String marketingHolder;

    /** 上市许可持有人地址 */
    private String marketingHolderAddress;

    /** 批准文号 */
    private String approvalNo;

    /** 批号 */
    private String batchNo;

    /** 生产日期 */
    private LocalDate productionDate;

    /** 有效期 */
    private LocalDate expiryDate;

    /** 货位 */
    private String location;

    /** 通知数量 */
    private BigDecimal notifyQty;

    /** 收货数量 */
    private BigDecimal receiveQty;

    /** 单价 */
    private BigDecimal price;

    /** 金额 */
    private BigDecimal amount;

    /** 收货拒收数量 */
    private BigDecimal rejectQty;

    /** 收货拒收金额 */
    private BigDecimal rejectAmount;

    /** 拒收原因 */
    private String rejectReason;

    /** 收货员 */
    private String receiver;

    /** 收货时间 */
    private LocalDateTime receiveTime;

    /** 源单据类型 */
    private String sourceType;

    /** 原单号 */
    private String sourceOrderNo;

    /** 备注 */
    private String remark;
}
