package com.adrug.erp.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 纯 ID + 审计字段实体基类（无租户/机构隔离字段）。
 * <p>
 * 用于平台级表（如 {@code tenant}），与 {@link BaseEntity} 的区别是：
 * 不含 {@code tenantId} / {@code orgId}。审计字段、逻辑删除、乐观锁与 {@link BaseEntity} 一致。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
public abstract class IdBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键（雪花 ID） */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 创建人（用户 ID） */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /** 更新人（用户 ID） */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    /** 逻辑删除标记：0-未删除，1-已删除 */
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;

    /** 乐观锁版本号 */
    @Version
    private Integer version;
}
