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
 * 通用实体基类。
 * <p>
 * 统一承载：主键（雪花 ID）、租户/机构隔离字段、审计字段、逻辑删除标记。
 * 配合 MyBatis-Plus 的自动填充（{@link FieldFill}）、逻辑删除（{@link TableLogic}）
 * 以及租户/机构拦截器实现「租户 → 机构 → 员工」的数据隔离与软删除。
 */
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键（雪花 ID） */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 租户 ID（SaaS 数据隔离） */
    @TableField(fill = FieldFill.INSERT)
    private Long tenantId;

    /** 机构 ID（药店/门店） */
    @TableField(fill = FieldFill.INSERT)
    private Long orgId;

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
