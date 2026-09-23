package com.adrug.erp.svc.bus.pms.entity;

import com.adrug.erp.common.entity.BusBaseEntity;
import com.adrug.erp.svc.bus.pms.enums.UnqualifiedHandleEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 验收单明细表实体（对应采购管理——验收单——明细）。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("acceptance_order_item")
public class AcceptanceOrderItem extends BusBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 验收单ID（关联 acceptance_order.id） */
    private Long orderId;

    /** 序号 */
    private Integer seqNo;

    /** 商品ID（关联商品信息） */
    private Long drugId;

    /** 商品编码 */
    private String drugCode;

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

    /** 生产许可证凭证号 */
    private String productionLicenseNo;

    /** 上市持有人 */
    private String marketingHolder;

    /** 上市持有人地址 */
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

    /** 收货数量 */
    private BigDecimal receivedQty;

    /** 验收数量 */
    private BigDecimal acceptQty;

    /** 单价 */
    private BigDecimal price;

    /** 金额 */
    private BigDecimal amount;

    /** 有药检报告：0-否，1-是 */
    private Integer hasInspectionReport;

    /** 监管码是否能识别：0-否，1-是 */
    private Integer supervisionCodeRecognizable;

    /** 包装情况 */
    private String packagingCondition;

    /** 外观质量 */
    private String appearanceQuality;

    /** 验收状态 */
    private String acceptanceStatus;

    /** 验收时间 */
    private LocalDateTime acceptTime;

    /** 抽样数量 */
    private BigDecimal sampleQty;

    /** 拒收数量 */
    private BigDecimal rejectQty;

    /** 拒收原因 */
    private String rejectReason;

    /** 拒收天数 */
    private Integer rejectDays;

    /** 不合格数量 */
    private BigDecimal unqualifiedQty;

    /** 不合格原因 */
    private String unqualifiedReason;

    /** 不合格处理方式（{@link UnqualifiedHandleEnum}） */
    private UnqualifiedHandleEnum handleType;

    /** 待处理数量 */
    private BigDecimal pendingQty;

    /** 收货员 */
    private String receiver;

    /** 备注 */
    private String remark;
}
