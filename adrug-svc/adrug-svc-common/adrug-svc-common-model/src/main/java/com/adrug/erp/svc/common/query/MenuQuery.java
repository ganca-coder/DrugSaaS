package com.adrug.erp.svc.common.query;

import com.adrug.erp.common.dto.PageQuery;
import com.adrug.erp.svc.common.enums.MenuStatusEnum;
import com.adrug.erp.svc.common.enums.MenuTypeEnum;
import com.adrug.erp.svc.common.enums.MenuVisibleEnum;
import lombok.Data;

/**
 * 菜单查询条件。
 * <p>
 * 枚举字段（菜单类型/是否显示/状态）以 code 值表示。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
@Data
public class MenuQuery extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 关键字：菜单名称 / 编码 模糊匹配 */
    private String keyword;

    /** 主键 */
    private Long id;

    /** 租户 ID（0-默认菜单，>0-租户自定义菜单） */
    private Long tenantId;

    /** 父菜单 ID（0-顶级） */
    private Long parentId;

    /** 菜单编码（精确匹配） */
    private String menuCode;

    /** 菜单名称（精确匹配） */
    private String menuName;

    /** 菜单类型，对应枚举类 {@link MenuTypeEnum} */
    private Integer menuType;

    /** 是否显示，对应枚举类 {@link MenuVisibleEnum} */
    private Integer visible;

    /** 状态，对应枚举类 {@link MenuStatusEnum} */
    private Integer status;
}
