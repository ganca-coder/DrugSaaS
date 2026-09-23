package com.adrug.erp.svc.common.repository;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.common.entity.Menu;
import com.adrug.erp.svc.common.query.MenuQuery;
import com.adrug.erp.svc.common.repository.impl.MenuRepositoryImpl;

import java.util.List;

/**
 * 菜单数据访问接口（依赖倒置契约）。
 * <p>
 * 屏蔽 MyBatis-Plus 细节，服务层只依赖本抽象；具体实现见 {@link MenuRepositoryImpl}。
 *
 * @author 甘成安
 * @date 2026/9/19
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
public interface MenuRepository {

    /**
     * 按租户 ID 查询全部菜单（不含逻辑删除，按排序号升序）。
     * <p>
     * {@code tenantId = 0} 查默认菜单；{@code tenantId > 0} 查租户自定义菜单。
     */
    List<Menu> selectByTenantId(Long tenantId);

    /**
     * 分页查询菜单。
     */
    PageResult<Menu> selectPage(MenuQuery query);

    /**
     * 按 ID 查询菜单。
     */
    Menu selectById(Long id);

    /**
     * 新增菜单（雪花主键回填到 {@code menu.id}）。
     */
    void insert(Menu menu);

    /**
     * 变更菜单。
     */
    void updateById(Menu menu);

    /**
     * 删除菜单（逻辑删除）。
     */
    void deleteById(Long id);

    /**
     * 物理删除菜单（预留）。
     *
     * @return 影响行数
     */
    int deletePhysically(Long id);
}
