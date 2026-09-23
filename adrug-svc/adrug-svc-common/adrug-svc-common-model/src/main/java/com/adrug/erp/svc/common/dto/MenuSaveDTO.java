package com.adrug.erp.svc.common.dto;

import com.adrug.erp.svc.common.enums.MenuStatusEnum;
import com.adrug.erp.svc.common.enums.MenuTypeEnum;
import com.adrug.erp.svc.common.enums.MenuVisibleEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜单新增/变更入参。
 * <p>
 * 与实体分离：审计字段、租户/机构字段由后端自动填充，不对外暴露。
 * 枚举字段（菜单类型/是否显示/状态）以 code 值表示，业务层再转换为枚举。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class MenuSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键（变更时必填） */
    private Long id;

    /** 菜单名称 */
    private String menuName;

    /** 菜单编码（租户自定义菜单覆盖默认菜单的匹配键） */
    private String menuCode;

    /** 父菜单 ID（0-顶级） */
    private Long parentId;

    /** 菜单类型，对应枚举类 {@link MenuTypeEnum} */
    private Integer menuType;

    /** 路由地址 */
    private String path;

    /** 前端组件路径（相对 src/views，不含 .vue 后缀） */
    private String component;

    /** 图标 */
    private String icon;

    /** 排序号 */
    private Integer sortNo;

    /** 是否显示，对应枚举类 {@link MenuVisibleEnum} */
    private Integer visible;

    /** 状态，对应枚举类 {@link MenuStatusEnum} */
    private Integer status;

    /** 权限标识 */
    private String permission;

    /** 备注 */
    private String remark;
}
