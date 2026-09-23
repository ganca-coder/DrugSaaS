package com.adrug.erp.svc.bus.oms.entity;

import com.adrug.erp.common.entity.BusBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * POS零售单主表实体（对应订单管理——POS零售单）。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("pos_retail_order")
public class PosRetailOrder extends BusBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 单据编号 */
    private String orderNo;

    /** 单据类型 */
    private String billType;

    /** 单据日期 */
    private LocalDate billDate;

    /** 销售类型 */
    private String saleType;

    /** POS流水号 */
    private String posSerialNo;

    /** 仓库ID（关联仓库信息） */
    private Long warehouseId;

    /** 仓库名称 */
    private String warehouseName;

    /** 营业时间 */
    private LocalDateTime businessTime;

    /** POS编号 */
    private String posNo;

    /** 班次 */
    private String shift;

    /** 会员积分是否已打印：0-否，1-是 */
    private Integer pointsPrinted;

    /** 收银员 */
    private String cashier;

    /** 营业员 */
    private String salesman;

    /** 合计数量 */
    private BigDecimal totalQty;

    /** 折后金额 */
    private BigDecimal discountedAmount;

    /** 优惠金额 */
    private BigDecimal discountAmount;

    /** 整单折扣率 */
    private BigDecimal wholeDiscountRate;

    /** 找零金额 */
    private BigDecimal changeAmount;

    /** 零钱包金额 */
    private BigDecimal walletAmount;

    /** 抹零金额 */
    private BigDecimal roundAmount;

    /** 手工抹零金额 */
    private BigDecimal manualRoundAmount;

    /** 会员ID（关联会员信息） */
    private Long memberId;

    /** 会员姓名 */
    private String memberName;

    /** 会员身份证号 */
    private String memberIdCardNo;

    /** 会员手机号 */
    private String memberPhone;

    /** POS来源单号 */
    private String posSourceOrderNo;

    /** 摘要 */
    private String summary;

    /** 业务平台 */
    private String businessPlatform;

    /** 零售价类型 */
    private String retailPriceType;

    /** 备注 */
    private String remark;
}
