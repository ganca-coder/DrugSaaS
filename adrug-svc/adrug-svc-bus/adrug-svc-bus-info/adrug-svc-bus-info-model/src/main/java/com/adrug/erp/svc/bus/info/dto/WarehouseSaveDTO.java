package com.adrug.erp.svc.bus.info.dto;

import com.adrug.erp.svc.bus.info.enums.WarehouseStatusEnum;
import com.adrug.erp.svc.bus.info.enums.WarehouseTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 仓库信息新增/变更入参。
 * <p>
 * 与实体分离：审计字段、租户/机构字段由后端自动填充，不对外暴露。
 * 枚举字段（仓库类型/状态）以 code 值表示。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class WarehouseSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键（变更时必填） */
    private Long id;

    /** 仓库编码 */
    private String warehouseCode;

    /** 仓库名称 */
    private String warehouseName;

    /** 仓库类型，对应枚举类 {@link WarehouseTypeEnum} */
    private Integer warehouseType;

    /** 仓库地址 */
    private String address;

    /** 负责人 */
    private String manager;

    /** 联系电话 */
    private String phone;

    /** 状态，对应枚举类 {@link WarehouseStatusEnum} */
    private Integer status;

    /** 备注 */
    private String remark;
}
