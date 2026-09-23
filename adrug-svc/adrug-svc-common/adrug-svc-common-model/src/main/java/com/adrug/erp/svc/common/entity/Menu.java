package com.adrug.erp.svc.common.entity;

import com.adrug.erp.common.entity.BaseEntity;
import com.adrug.erp.svc.common.enums.MenuStatusEnum;
import com.adrug.erp.svc.common.enums.MenuTypeEnum;
import com.adrug.erp.svc.common.enums.MenuVisibleEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 菜单实体（对应通用服务——菜单管理）。
 * <p>
 * 通过 {@code tenant_id} 区分默认菜单与租户自定义菜单：
 * <ul>
 *   <li>{@code tenant_id = 0}：默认菜单，所有租户共享；</li>
 *   <li>{@code tenant_id > 0}：租户自定义菜单，按 {@code menuCode} 覆盖默认菜单同名项。</li>
 * </ul>
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Getter
@Setter
@TableName("menu")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 菜单名称 */
    private String menuName;

    /** 菜单编码（租户自定义菜单覆盖默认菜单的匹配键） */
    private String menuCode;

    /** 父菜单 ID（0-顶级） */
    private Long parentId;

    /** 菜单类型（{@link MenuTypeEnum}） */
    private MenuTypeEnum menuType;

    /** 路由地址 */
    private String path;

    /** 前端组件路径（相对 src/views，不含 .vue 后缀） */
    private String component;

    /** 图标 */
    private String icon;

    /** 排序号 */
    private Integer sortNo;

    /** 是否显示（{@link MenuVisibleEnum}） */
    private MenuVisibleEnum visible;

    /** 状态（{@link MenuStatusEnum}） */
    private MenuStatusEnum status;

    /** 权限标识 */
    private String permission;

    /** 备注 */
    private String remark;
}
