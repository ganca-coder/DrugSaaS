package com.adrug.erp.svc.bus.oms.query;

import com.adrug.erp.common.dto.PageQuery;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * POS零售单查询条件。
 * <p>
 * 覆盖 {@code PosRetailOrder} 的全部业务与审计字段，并针对日期/时间字段提供范围查询。
 *
 * @author 甘成安
 * @date 2026/9/22
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class PosRetailOrderQuery extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 关键字：单据编号 / 会员姓名 / 会员手机号 模糊匹配 */
    private String keyword;

    /** 主键 */
    private Long id;

    /** 租户 ID */
    private Long tenantId;

    /** 机构 ID（药店/门店） */
    private Long orgId;

    /** 单据编号（精确匹配） */
    private String orderNo;

    /** 单据类型（精确匹配） */
    private String billType;

    /** 销售类型（精确匹配） */
    private String saleType;

    /** POS流水号（精确匹配） */
    private String posSerialNo;

    /** 仓库ID */
    private Long warehouseId;

    /** POS编号（精确匹配） */
    private String posNo;

    /** 班次（精确匹配） */
    private String shift;

    /** 会员积分是否已打印：0-否，1-是 */
    private Integer pointsPrinted;

    /** 收银员（精确匹配） */
    private String cashier;

    /** 营业员（精确匹配） */
    private String salesman;

    /** 会员ID */
    private Long memberId;

    /** 会员手机号（精确匹配） */
    private String memberPhone;

    /** 业务平台（精确匹配） */
    private String businessPlatform;

    /** 零售价类型（精确匹配） */
    private String retailPriceType;

    /** 单据日期范围-起 */
    private LocalDate billDateStart;

    /** 单据日期范围-止 */
    private LocalDate billDateEnd;

    /** 营业时间范围-起 */
    private LocalDateTime businessTimeStart;

    /** 营业时间范围-止 */
    private LocalDateTime businessTimeEnd;

    /** 创建人（用户 ID） */
    private Long createUserId;

    /** 更新人（用户 ID） */
    private Long updateUserId;

    /** 创建时间范围-起 */
    private LocalDateTime createTimeStart;

    /** 创建时间范围-止 */
    private LocalDateTime createTimeEnd;

    /** 更新时间范围-起 */
    private LocalDateTime updateTimeStart;

    /** 更新时间范围-止 */
    private LocalDateTime updateTimeEnd;
}
