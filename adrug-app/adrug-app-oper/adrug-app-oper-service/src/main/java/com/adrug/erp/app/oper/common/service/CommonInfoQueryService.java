package com.adrug.erp.app.oper.common.service;

import com.adrug.erp.common.dto.PageResult;
import com.adrug.erp.svc.common.query.MenuQuery;
import com.adrug.erp.svc.common.vo.MenuVO;

import java.util.List;

/**
 * 菜单查询服务接口（应用层，读写分离——读）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public interface CommonInfoQueryService {

    /**
     * 查询当前租户生效菜单树。
     */
    List<MenuVO> getMenuTree();

    /**
     * 分页查询菜单。
     */
    PageResult<MenuVO> getMenuPage(MenuQuery query);

    /**
     * 按 ID 查询菜单。
     */
    MenuVO getMenuById(Long id);
}
