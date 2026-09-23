package com.adrug.erp.svc.bus.oms.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * POS零售单视图对象（对外返回，不含租户、逻辑删除等内部字段）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class PosRetailOrderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String orderNo;

    private String billType;

    private LocalDate billDate;

    private String saleType;

    private String posSerialNo;

    private Long warehouseId;

    private String warehouseName;

    private LocalDateTime businessTime;

    private String posNo;

    private String shift;

    /** 会员积分是否已打印：0-否，1-是 */
    private Integer pointsPrinted;

    private String cashier;

    private String salesman;

    private BigDecimal totalQty;

    private BigDecimal discountedAmount;

    private BigDecimal discountAmount;

    private BigDecimal wholeDiscountRate;

    private BigDecimal changeAmount;

    private BigDecimal walletAmount;

    private BigDecimal roundAmount;

    private BigDecimal manualRoundAmount;

    private Long memberId;

    private String memberName;

    private String memberIdCardNo;

    private String memberPhone;

    private String posSourceOrderNo;

    private String summary;

    private String businessPlatform;

    private String retailPriceType;

    private String remark;

    private Long orgId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /** POS零售单明细 */
    private List<PosRetailOrderItemVO> items;

    /**
     * 会员积分是否已打印描述
     */
    public String getPointsPrintedDesc() {
        return pointsPrinted != null && pointsPrinted == 1 ? "是" : "否";
    }
}
