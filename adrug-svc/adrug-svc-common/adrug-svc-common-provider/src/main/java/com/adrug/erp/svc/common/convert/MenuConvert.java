package com.adrug.erp.svc.common.convert;

import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.common.dto.MenuSaveDTO;
import com.adrug.erp.svc.common.entity.Menu;
import com.adrug.erp.svc.common.enums.MenuStatusEnum;
import com.adrug.erp.svc.common.enums.MenuTypeEnum;
import com.adrug.erp.svc.common.enums.MenuVisibleEnum;
import com.adrug.erp.svc.common.vo.MenuVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单实体 &lt;-&gt; 传输对象 转换器。
 * <p>
 * 传输对象（DTO/VO）中的枚举字段用 code 表示，实体用枚举类型；
 * 转换时通过 {@link BaseEnum#ofCode} 在 code 与枚举之间切换。
 * 树构建 {@link #toTree(List)} 与租户覆盖合并 {@link #mergeTree(List, List)} 也收敛在此。
 */
public final class MenuConvert {

    private MenuConvert() {
    }

    /**
     * 实体转视图对象（枚举 → code，不含子节点）。
     */
    public static MenuVO toVO(Menu menu) {
        MenuVO vo = new MenuVO();
        BeanUtils.copyProperties(menu, vo);
        if (menu.getMenuType() != null) {
            vo.setMenuType(menu.getMenuType().getCode());
        }
        if (menu.getVisible() != null) {
            vo.setVisible(menu.getVisible().getCode());
        }
        if (menu.getStatus() != null) {
            vo.setStatus(menu.getStatus().getCode());
        }
        return vo;
    }

    /**
     * 实体列表转视图对象列表。
     */
    public static List<MenuVO> toVOList(List<Menu> menus) {
        return menus.stream()
                .map(MenuConvert::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 入参转实体（code → 枚举）。
     */
    public static Menu toEntity(MenuSaveDTO dto) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(dto, menu);
        menu.setMenuType(BaseEnum.ofCode(MenuTypeEnum.class, dto.getMenuType()));
        menu.setVisible(BaseEnum.ofCode(MenuVisibleEnum.class, dto.getVisible()));
        menu.setStatus(BaseEnum.ofCode(MenuStatusEnum.class, dto.getStatus()));
        return menu;
    }

    /**
     * 扁平菜单列表构建为树（根节点列表，子节点挂在 children）。
     * <p>
     * 按 {@code parentId} 归组，父节点缺失（parentId 非 0 但不存在）时按根节点处理，
     * 避免脏数据导致节点丢失；每个层级按 sortNo 升序、id 升序排序。
     */
    public static List<MenuVO> toTree(List<Menu> menus) {
        if (menus == null || menus.isEmpty()) {
            return Collections.emptyList();
        }
        Map<Long, MenuVO> voMap = new LinkedHashMap<>();
        for (Menu menu : menus) {
            voMap.put(menu.getId(), toVO(menu));
        }
        List<MenuVO> roots = new ArrayList<>();
        for (MenuVO vo : voMap.values()) {
            Long parentId = vo.getParentId();
            MenuVO parent = (parentId != null && parentId != 0) ? voMap.get(parentId) : null;
            if (parent != null) {
                parent.getChildren().add(vo);
            } else {
                roots.add(vo);
            }
        }
        sortTree(roots);
        return roots;
    }

    /**
     * 租户自定义菜单树覆盖默认菜单树（按 menuCode 递归合并）。
     * <p>
     * 规则：默认菜单树为底；租户自定义树中 menuCode 相同的节点覆盖默认节点（标量字段以租户为准，
     * 子节点继续递归合并），租户独有的节点直接追加。返回合并后的根节点列表。
     */
    public static List<MenuVO> mergeTree(List<MenuVO> base, List<MenuVO> override) {
        if (override == null || override.isEmpty()) {
            return base;
        }
        if (base == null || base.isEmpty()) {
            return override;
        }

        Map<String, MenuVO> result = new LinkedHashMap<>();
        for (MenuVO b : base) {
            result.put(b.getMenuCode(), b);
        }
        for (MenuVO o : override) {
            String code = o.getMenuCode();
            if (code != null && result.containsKey(code)) {
                MenuVO existing = result.get(code);
                o.setChildren(mergeTree(existing.getChildren(), o.getChildren()));
            }
            result.put(code, o);
        }
        List<MenuVO> merged = new ArrayList<>(result.values());
        sortTree(merged);
        return merged;
    }

    /**
     * 按用户功能权限过滤菜单树。
     * <p>
     * 有权限标识的菜单需用户具备该权限才显示；无权限标识的目录需有可见子节点才显示；
     * 无权限标识的叶子菜单（如首页）始终显示。{@code *} / {@code *:*} 视为超级管理员不过滤。
     */
    public static List<MenuVO> filterByPermissions(List<MenuVO> tree, List<String> permissions) {
        if (permissions == null) {
            return tree;
        }
        if (permissions.contains("*") || permissions.contains("*:*")) {
            return tree;
        }
        List<MenuVO> result = new ArrayList<>();
        for (MenuVO node : tree) {
            MenuVO filtered = filterNode(node, permissions);
            if (filtered != null) {
                result.add(filtered);
            }
        }
        return result;
    }

    private static MenuVO filterNode(MenuVO node, List<String> permissions) {
        List<MenuVO> children = new ArrayList<>();
        if (node.getChildren() != null) {
            for (MenuVO child : node.getChildren()) {
                MenuVO filtered = filterNode(child, permissions);
                if (filtered != null) {
                    children.add(filtered);
                }
            }
        }
        node.setChildren(children);

        // 按钮类型不进菜单树
        if (node.getMenuType() != null && node.getMenuType() == 3) {
            return null;
        }
        // 有权限标识：必须有该权限才显示
        if (node.getPermission() != null && !node.getPermission().isEmpty()) {
            return permissions.contains(node.getPermission()) ? node : null;
        }
        // 无权限标识：目录需有可见子节点，叶子菜单（首页）始终显示
        if (node.getMenuType() != null && node.getMenuType() == 1) {
            return children.isEmpty() ? null : node;
        }
        return node;
    }

    /**
     * 递归排序：sortNo 升序，id 升序兜底。
     */
    private static void sortTree(List<MenuVO> nodes) {
        nodes.sort(Comparator.comparing(MenuVO::getSortNo, Comparator.nullsLast(Integer::compareTo))
                .thenComparing(MenuVO::getId, Comparator.nullsLast(Long::compareTo)));
        for (MenuVO node : nodes) {
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                sortTree(node.getChildren());
            }
        }
    }
}
