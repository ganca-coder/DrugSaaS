package com.adrug.erp.svc.bus.pms.vo;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.bus.pms.enums.BillStatusEnum;
import com.adrug.erp.svc.bus.pms.enums.SourceTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 收货单视图对象（对外返回，不含租户、逻辑删除等内部字段）。
 * <p>
 * 枚举字段（数据来源/单据状态）以 code 值表示，desc 字段通过 {@code getXxxDesc()} 提供。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class ReceivingOrderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String orderNo;

    /** 数据来源，对应枚举类 {@link SourceTypeEnum} */
    private Integer sourceType;

    private Long sourceOrderId;

    private Long supplierId;

    private String supplierName;

    private Long warehouseId;

    private String warehouseName;

    private String arrivalNo;

    private LocalDate receiveDate;

    private String handler;

    private String receiver;

    private LocalDateTime receiveStartTime;

    /** 单据状态，对应枚举类 {@link BillStatusEnum} */
    private Integer status;

    private BigDecimal totalQty;

    private LocalDateTime postTime;

    private String remark;

    private Long orgId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /** 收货单明细 */
    private List<ReceivingOrderItemVO> items;

    /**
     * 数据来源描述
     */
    public String getSourceTypeDesc() {
        SourceTypeEnum sourceTypeEnum = BaseEnum.ofCode(SourceTypeEnum.class, sourceType);
        return sourceTypeEnum == null ? null : sourceTypeEnum.getDesc();
    }

    /**
     * 单据状态描述
     */
    public String getStatusDesc() {
        BillStatusEnum statusEnum = BaseEnum.ofCode(BillStatusEnum.class, status);
        return statusEnum == null ? null : statusEnum.getDesc();
    }
}
