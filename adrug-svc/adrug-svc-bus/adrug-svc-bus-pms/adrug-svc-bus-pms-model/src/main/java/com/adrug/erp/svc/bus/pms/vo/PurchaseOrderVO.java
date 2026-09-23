package com.adrug.erp.svc.bus.pms.vo;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.bus.pms.enums.BillStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 采购订单视图对象（对外返回，不含租户、逻辑删除等内部字段）。
 * <p>
 * 枚举字段（单据状态）以 code 值表示，desc 字段通过 {@code getStatusDesc()} 提供。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class PurchaseOrderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String orderNo;

    private Long supplierId;

    private String supplierName;

    private String supplierSalesman;

    private LocalDate orderDate;

    private String handler;

    private String purchaser;

    private String paymentMethod;

    private String settlementMethod;

    private LocalDate preSettlementDate;

    private String invoiceType;

    private String deliveryMethod;

    private LocalDateTime estimatedArrivalTime;

    /** 单据状态，对应枚举类 {@link BillStatusEnum} */
    private Integer status;

    private BigDecimal totalQty;

    private BigDecimal totalAmount;

    private LocalDateTime postTime;

    private String cancelReason;

    private String cancelNote;

    private String remark;

    private Long orgId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /** 采购订单明细 */
    private List<PurchaseOrderItemVO> items;

    /**
     * 单据状态描述
     */
    public String getStatusDesc() {
        BillStatusEnum statusEnum = BaseEnum.ofCode(BillStatusEnum.class, status);
        return statusEnum == null ? null : statusEnum.getDesc();
    }
}
