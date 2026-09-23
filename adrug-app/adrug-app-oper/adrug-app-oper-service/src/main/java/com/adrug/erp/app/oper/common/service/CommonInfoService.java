package com.adrug.erp.app.oper.common.service;

import com.adrug.erp.svc.common.dto.MenuSaveDTO;

/**
 * 菜单服务接口（应用层，读写分离——写）。
 *
 * @author 甘成安
 * @date 2026/9/21
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 */
public interface CommonInfoService {

    /**
     * 新增菜单。
     *
     * @return 新菜单主键
     */
    Long createMenu(MenuSaveDTO dto);

    /**
     * 变更菜单。
     */
    void updateMenu(Long id, MenuSaveDTO dto);

    /**
     * 删除菜单（逻辑删除）。
     */
    void deleteMenu(Long id);
}
