package com.adrug.erp.common.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务实体基类（非通用库）。
 * <p>
 * 在通用 {@link BaseEntity} 基础上增加定制化字段 {@code cust1} ~ {@code cust6}，
 * 供业务库（如 {@code adrug-bus-info}）的实体继承。
 * <p>
 * 通用库（{@code adrug_common}）的实体直接继承 {@link BaseEntity}，不含定制化字段。
 */
@Getter
@Setter
public abstract class BusBaseEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 定制化字段1 */
    private String cust1;

    /** 定制化字段2 */
    private String cust2;

    /** 定制化字段3 */
    private String cust3;

    /** 定制化字段4 */
    private String cust4;

    /** 定制化字段5 */
    private String cust5;

    /** 定制化字段6 */
    private String cust6;
}
