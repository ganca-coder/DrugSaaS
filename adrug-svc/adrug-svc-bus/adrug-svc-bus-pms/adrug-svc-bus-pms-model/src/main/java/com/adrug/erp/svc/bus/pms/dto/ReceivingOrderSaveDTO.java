package com.adrug.erp.svc.bus.pms.dto;

import com.adrug.erp.svc.bus.pms.enums.SourceTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 收货单新增/变更入参。
 * <p>
 * 与实体分离：审计字段、租户/机构字段、单据状态、过账时间、数量合计由后端处理，不对外暴露。
 * 明细通过 {@code items} 一并提交。
 *
 * @author 甘成安
 * @date 2026/9/20
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class ReceivingOrderSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键（变更时必填） */
    private Long id;

    /** 收货单号 */
    private String orderNo;

    /** 数据来源，对应枚举类 {@link SourceTypeEnum} */
    private Integer sourceType;

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

    /** 备注 */
    private String remark;

    /** 收货单明细 */
    private List<ReceivingOrderItemDTO> items;
}
