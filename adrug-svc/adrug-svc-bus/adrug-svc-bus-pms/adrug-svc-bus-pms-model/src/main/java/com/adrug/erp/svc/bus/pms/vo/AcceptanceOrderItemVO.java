package com.adrug.erp.svc.bus.pms.vo;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.bus.pms.enums.UnqualifiedHandleEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 验收单明细视图对象。
 * <p>
 * 枚举字段（不合格处理方式）以 code 值表示，desc 字段通过 {@code getHandleTypeDesc()} 提供。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class AcceptanceOrderItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long orderId;

    private Integer seqNo;

    private Long drugId;

    private String drugCode;

    private String genericName;

    private String drugName;

    private String spec;

    private String unit;

    private String dosageForm;

    private String origin;

    private String manufacturer;

    private String productionLicenseNo;

    private String marketingHolder;

    private String marketingHolderAddress;

    private String approvalNo;

    private String batchNo;

    private LocalDate productionDate;

    private LocalDate expiryDate;

    private String location;

    private BigDecimal receivedQty;

    private BigDecimal acceptQty;

    private BigDecimal price;

    private BigDecimal amount;

    private Integer hasInspectionReport;

    private Integer supervisionCodeRecognizable;

    private String packagingCondition;

    private String appearanceQuality;

    private String acceptanceStatus;

    private LocalDateTime acceptTime;

    private BigDecimal sampleQty;

    private BigDecimal rejectQty;

    private String rejectReason;

    private Integer rejectDays;

    private BigDecimal unqualifiedQty;

    private String unqualifiedReason;

    /** 不合格处理方式，对应枚举类 {@link UnqualifiedHandleEnum} */
    private Integer handleType;

    private BigDecimal pendingQty;

    private String receiver;

    private String remark;

    /**
     * 不合格处理方式描述
     */
    public String getHandleTypeDesc() {
        UnqualifiedHandleEnum handleTypeEnum = BaseEnum.ofCode(UnqualifiedHandleEnum.class, handleType);
        return handleTypeEnum == null ? null : handleTypeEnum.getDesc();
    }
}
