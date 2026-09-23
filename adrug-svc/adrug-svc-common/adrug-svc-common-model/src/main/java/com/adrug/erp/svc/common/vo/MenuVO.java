package com.adrug.erp.svc.common.vo;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.common.enums.MenuStatusEnum;
import com.adrug.erp.svc.common.enums.MenuTypeEnum;
import com.adrug.erp.svc.common.enums.MenuVisibleEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单视图对象（对外返回，不含租户、逻辑删除等内部字段）。
 * <p>
 * 枚举字段（菜单类型/是否显示/状态）以 code 值表示，desc 字段通过 {@code getXxxDesc()} 提供；
 * {@code children} 承载树形子菜单。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class MenuVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String menuName;

    private String menuCode;

    private Long parentId;

    /** 菜单类型，对应枚举类 {@link MenuTypeEnum} */
    private Integer menuType;

    private String path;

    private String component;

    private String icon;

    private Integer sortNo;

    /** 是否显示，对应枚举类 {@link MenuVisibleEnum} */
    private Integer visible;

    /** 状态，对应枚举类 {@link MenuStatusEnum} */
    private Integer status;

    private String permission;

    private String remark;

    /** 子菜单（树形结构） */
    private List<MenuVO> children = new ArrayList<>();

    /**
     * 菜单类型描述
     */
    public String getMenuTypeDesc() {
        MenuTypeEnum typeEnum = BaseEnum.ofCode(MenuTypeEnum.class, menuType);
        return typeEnum == null ? null : typeEnum.getDesc();
    }

    /**
     * 显示状态描述
     */
    public String getVisibleDesc() {
        MenuVisibleEnum visibleEnum = BaseEnum.ofCode(MenuVisibleEnum.class, visible);
        return visibleEnum == null ? null : visibleEnum.getDesc();
    }

    /**
     * 启用状态描述
     */
    public String getStatusDesc() {
        MenuStatusEnum statusEnum = BaseEnum.ofCode(MenuStatusEnum.class, status);
        return statusEnum == null ? null : statusEnum.getDesc();
    }
}
