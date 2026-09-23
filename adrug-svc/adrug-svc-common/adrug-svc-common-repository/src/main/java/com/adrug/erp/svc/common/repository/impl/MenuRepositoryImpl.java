package com.adrug.erp.svc.common.repository.impl;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.common.enums.BaseEnum;
import com.adrug.erp.svc.common.entity.Menu;
import com.adrug.erp.svc.common.enums.MenuStatusEnum;
import com.adrug.erp.svc.common.enums.MenuTypeEnum;
import com.adrug.erp.svc.common.enums.MenuVisibleEnum;
import com.adrug.erp.svc.common.mapper.MenuMapper;
import com.adrug.erp.svc.common.query.MenuQuery;
import com.adrug.erp.svc.common.repository.MenuRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 菜单数据访问实现。
 * <p>
 * 封装 MyBatis-Plus 的 Mapper 调用与查询条件构建，向上层暴露领域友好的方法。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Repository
public class MenuRepositoryImpl implements MenuRepository {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> selectByTenantId(Long tenantId) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getTenantId, tenantId);
        wrapper.orderByAsc(Menu::getSortNo);
        wrapper.orderByAsc(Menu::getId);
        return menuMapper.selectList(wrapper);
    }

    @Override
    public PageResult<Menu> selectPage(MenuQuery query) {
        Page<Menu> page = new Page<>(query.getPageNum(), query.getPageSize());

        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        // 关键字：菜单名称 或 菜单编码 模糊匹配
        wrapper.and(StringUtils.hasText(query.getKeyword()), w ->
                w.like(Menu::getMenuName, query.getKeyword())
                        .or()
                        .like(Menu::getMenuCode, query.getKeyword()));

        wrapper.eq(query.getId() != null, Menu::getId, query.getId());
        wrapper.eq(query.getTenantId() != null, Menu::getTenantId, query.getTenantId());
        wrapper.eq(query.getParentId() != null, Menu::getParentId, query.getParentId());
        wrapper.eq(StringUtils.hasText(query.getMenuCode()), Menu::getMenuCode, query.getMenuCode());
        wrapper.eq(StringUtils.hasText(query.getMenuName()), Menu::getMenuName, query.getMenuName());
        MenuTypeEnum menuType = BaseEnum.ofCode(MenuTypeEnum.class, query.getMenuType());
        wrapper.eq(menuType != null, Menu::getMenuType, menuType);
        MenuVisibleEnum visible = BaseEnum.ofCode(MenuVisibleEnum.class, query.getVisible());
        wrapper.eq(visible != null, Menu::getVisible, visible);
        MenuStatusEnum status = BaseEnum.ofCode(MenuStatusEnum.class, query.getStatus());
        wrapper.eq(status != null, Menu::getStatus, status);

        wrapper.orderByAsc(Menu::getSortNo);
        wrapper.orderByAsc(Menu::getId);

        Page<Menu> result = menuMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public Menu selectById(Long id) {
        return menuMapper.selectById(id);
    }

    @Override
    public void insert(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void updateById(Menu menu) {
        menuMapper.updateById(menu);
    }

    @Override
    public void deleteById(Long id) {
        menuMapper.deleteById(id);
    }

    @Override
    public int deletePhysically(Long id) {
        return menuMapper.deletePhysically(id);
    }
}
