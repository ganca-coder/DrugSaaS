package com.adrug.erp.svc.bus.pms.entity;

import com.adrug.erp.common.entity.BusBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 拒收单明细表实体（对应采购管理——拒收单——明细）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("rejection_order_item")
public class RejectionOrderItem extends BusBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 拒收单ID（关联 rejection_order.id） */
    private Long orderId;

    /** 序号 */
    private Integer seqNo;

    /** 商品ID（关联商品信息） */
    private Long drugId;

    /** 商品编码 */
    private String drugCode;

    /** 商品名称 */
    private String drugName;

    /** 单位 */
    private String unit;

    /** 规格 */
    private String spec;

    /** 剂型 */
    private String dosageForm;

    /** 产地 */
    private String origin;

    /** 批号 */
    private String batchNo;

    /** 生产日期 */
    private LocalDate productionDate;

    /** 有效期 */
    private LocalDate expiryDate;

    /** 拒收数量 */
    private BigDecimal rejectQty;

    /** 拒收原因 */
    private String rejectReason;

    /** 备注 */
    private String remark;
}
