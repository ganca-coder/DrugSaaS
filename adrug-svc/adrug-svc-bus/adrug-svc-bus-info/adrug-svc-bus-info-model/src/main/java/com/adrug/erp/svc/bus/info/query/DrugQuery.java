package com.adrug.erp.svc.bus.info.query;

import com.adrug.erp.common.dto.PageQuery;
import com.adrug.erp.svc.bus.info.enums.DrugStatusEnum;
import com.adrug.erp.svc.bus.info.enums.DrugTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 药品信息查询条件。
 * <p>
 * 覆盖 {@code Drug} 的全部业务与审计字段。枚举字段（药品类型/状态）以 code 值表示。
 *
 * @author 甘成安
 * @date 2026/9/18
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class DrugQuery extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 关键字：药品名称 / 药品编码 模糊匹配 */
    private String keyword;

    /** 主键 */
    private Long id;

    /** 租户 ID */
    private Long tenantId;

    /** 机构 ID（药店/门店） */
    private Long orgId;

    /** 药品编码（精确匹配） */
    private String drugCode;

    /** 药品名称（精确匹配） */
    private String drugName;

    /** 通用名（精确匹配） */
    private String genericName;

    /** 规格（精确匹配） */
    private String spec;

    /** 剂型（精确匹配） */
    private String dosageForm;

    /** 单位（精确匹配） */
    private String unit;

    /** 生产厂家（精确匹配） */
    private String manufacturer;

    /** 批准文号（精确匹配） */
    private String approvalNo;

    /** 药品类型，对应枚举类 {@link DrugTypeEnum} */
    private Integer drugType;

    /** 状态，对应枚举类 {@link DrugStatusEnum} */
    private Integer status;

    /** 备注（精确匹配） */
    private String remark;

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
